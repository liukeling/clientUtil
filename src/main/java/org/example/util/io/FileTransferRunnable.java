package org.example.util.io;

import org.example.util.Contants;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

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
        long fileSize = sendFile.length();
        long curSend = 0;
        ops.write(Contants.fileContentTag);
        FileInputStream fis = new FileInputStream(sendFile);
        byte[] tmp = new byte[10];
        int len;
        while((len = fis.read(tmp)) != -1){
            ops.write(tmp,0, len);
            ops.flush();
            curSend += len;
            try {
                setProcess(Long.valueOf(curSend * 100 / fileSize).intValue());
            }catch (Exception e){

            }
        }
        fis.close();
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
