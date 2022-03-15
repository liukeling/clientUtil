package org.example.util.io;

import org.example.util.Contants;
import org.example.util.listener.ProcessListner;
import org.example.util.listener.adapter.ProcessListnerAdapter;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class FileTransferRunnable extends BaseIORunnable {
    private File sendFile;
    private Socket socket;

    public FileTransferRunnable(File sendFile, Socket socket) throws IOException {
        super(socket.getInputStream(), socket.getOutputStream());
        this.sendFile = sendFile;
        this.socket = socket;
    }

    @Override
    protected void ioHandle(byte[] lastInfo) throws IOException {
        sendFileInfo();
        ops.write(Contants.fileContentTag);
        FileInputStream fis = new FileInputStream(sendFile);

        IOHandleThreadUtil ioSendUtil = new IOHandleThreadUtil(fis, ops, true, false);
        ProcessListner listner = new ProcessListnerAdapter(){
            @Override
            public void updateProcess(Map<String, Object> processInfo) {
                Object info = processInfo.get("info");
                if(info != null){
                    setProcess(Long.valueOf(Long.valueOf(info.toString()) * 100 / sendFile.length()).intValue());
                }
            }
        };
        ioSendUtil.start(listner);
        synchronized (ioSendUtil) {
            try {
                ioSendUtil.wait();
            }catch (Exception e){
                e.printStackTrace();
                ioSendUtil.stop();
            }
        }
        System.out.println("=====wait end====");
        ops.write(Contants.endTag);
        ops.flush();
    }
    private void sendFileInfo() throws IOException{

        ops.write(Contants.beginTag);
        ops.write(Contants.fileNameTag);
        ops.write(sendFile.getName().getBytes());
        ops.write(Contants.reLineTag);
        ops.write(Contants.fileSizeTag);
        ops.write(String.valueOf(sendFile.length()).getBytes());
        ops.write(Contants.reLineTag);
        ops.flush();
    }
    private void setProcess(int process){
        if(processListner != null) {
            HashMap<String, Object> info = new HashMap<>(1);
            info.put("taskId", taskId);
            processListner.begin(info);
            info.put("process", process);
            processListner.updateProcess(info);
        }
    }
    @Override
    protected void finallyHandle() {
        if(ops != null){
            try {
                ops.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(ips != null){
            try {
                ips.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(processListner != null) {
            HashMap<String, Object> info = new HashMap<>(1);
            info.put("taskId", taskId);
            processListner.end(info);
        }
    }

}
