package org.example.views.file.runnable;

import org.example.util.Contants;
import org.example.util.ThreadUtil;
import org.example.util.io.FileReceiveRunnable;
import org.example.util.listener.adapter.ProcessListnerAdapter;
import org.example.views.SyncList;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * @author liukeling
 */
public class ReceiveServerRunnable implements Runnable {
    private ServerSocket server;
    private JTextField infoShow;
    private SyncList list;
    public ReceiveServerRunnable(JTextField infoShow, SyncList list){
        this.infoShow = infoShow;
        this.list = list;
    }
    @Override
    public void run() {
        try {
            server =  new ServerSocket(Contants.port);
            if(infoShow != null) {
                infoShow.setText("接收就绪");
            }
            while(!server.isClosed()){
                Socket socket = server.accept();
                FileReceiveRunnable fileReceiveRunnable = new FileReceiveRunnable(socket);
                fileReceiveRunnable.setProcessListner(new ProcessListnerAdapter(){
                    @Override
                    public void updateProcess(Map<String, Object> processInfo) {
                        Object taskId = processInfo.get("taskId");
                        Object info = processInfo.get("info");
                        if(list != null && taskId != null && info != null){
                            list.putInfo(taskId.toString(), info.toString());
                        }
                    }

                    @Override
                    public void end(Map<String, Object> processInfo) {
                        Object taskId = processInfo.get("taskId");
                        if(list != null && taskId != null){
                            try {
                                list.removeInfo(taskId.toString());
                            }catch (Exception e){

                            }
                        }
                    }
                });
                ThreadUtil.execute(fileReceiveRunnable);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeServer();
            if(infoShow != null) {
                infoShow.setText("接收中断");
            }
        }
    }
    public void closeServer(){
        if(server != null){
            if(!server.isClosed()){
                try {
                    server.close();
                    server = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
