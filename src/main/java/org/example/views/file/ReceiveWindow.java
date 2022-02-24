package org.example.views.file;

import org.example.util.ThreadUtil;
import org.example.util.file.FileReceiveRunnable;
import org.example.views.BaseWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 接收文件
 */
public class ReceiveWindow extends BaseWindow {
    private JTextField infoShow = new JTextField("准备中...");
    public ReceiveWindow(String title) {
        super(title);
        frame.setSize(500,100);
        clean();
        frame.add(infoShow);
        infoShow.setEditable(false);
        frame.validate();
        initReceive();
    }
    private void initReceive(){
        try {
            ServerSocket server = new ServerSocket(1818);
            infoShow.setText("接收中。。。");
            while(true){
                Socket socket = server.accept();
                ThreadUtil.execute(new FileReceiveRunnable(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
            infoShow.setText("open port failed");
        }
    }
}
