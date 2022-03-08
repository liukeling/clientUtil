package org.example.views.file.runnable;

import org.example.util.ThreadUtil;
import org.example.util.io.FileReceiveRunnable;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liukeling
 */
public class ReceiveServerRunnable implements Runnable {
    private ServerSocket server;
    private JTextField infoShow;
    public ReceiveServerRunnable(JTextField infoShow){
        this.infoShow = infoShow;
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
                ThreadUtil.execute(new FileReceiveRunnable(socket));
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
