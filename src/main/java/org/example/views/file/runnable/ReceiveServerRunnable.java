package org.example.views.file.runnable;

import org.example.util.ThreadUtil;
import org.example.util.io.FileReceiveRunnable;
import org.example.views.SyncList;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
            server =  new ServerSocket(1818);
            if(infoShow != null) {
                infoShow.setText("接收就绪");
            }
            while(!server.isClosed()){
                Socket socket = server.accept();
                ThreadUtil.execute(new FileReceiveRunnable(socket, list));
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
