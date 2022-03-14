package org.example.views.file;

import org.example.util.ProcessUtil;
import org.example.util.ThreadUtil;
import org.example.util.listener.adapter.ProcessListnerAdapter;
import org.example.views.BaseWindow;
import org.example.views.InfoWindow;
import org.example.util.io.FileTransferRunnable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class FransferInfoWindow extends BaseWindow {
    private BorderLayout layout = new BorderLayout();
    private JTextField ipFiled = new JTextField("localhost", 10);
    private JTextField portFiled = new JTextField("1818", 10);
    private JLabel processLable = new JLabel();
    private JButton send = new JButton("确定");
    private FileTransferRunnable transferRunnable = null;
    private final File readyFile;
    private boolean runing = false;
    public FransferInfoWindow(final File readyFile) {
        super(readyFile.getName());
        this.readyFile = readyFile;
        frame.setSize(500, 300);
        initFrame();
        send.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                synchronized (readyFile) {
                    if(!runing) {
                        sendTodo();
                    }
                }
            }
        });
    }

    private void initFrame() {
        clean();
        frame.setLayout(layout);

        frame.add(new JLabel("请输入接收方地址"), BorderLayout.NORTH);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            if (i == 4) {
                JPanel center = new JPanel();
                jPanel.add(center);

                center.add(new JLabel("ip："));
                center.add(ipFiled);
                center.add(new JLabel("port："));
                center.add(portFiled);
            } else if (i == 7) {
                jPanel.add(processLable);
                processLable.setText(ProcessUtil.strView(0));
            } else {
                jPanel.add(new JPanel());
            }
        }
        frame.add(jPanel, BorderLayout.CENTER);
        JPanel south = new JPanel();
        send.setSize(300, 200);
        south.add(send);
        frame.add(south, BorderLayout.SOUTH);
        frame.validate();
    }

    private void sendTodo() {
        if (readyFile != null && readyFile.exists() && readyFile.isFile()) {
            Socket socket = null;
            try {
                socket = new Socket(ipFiled.getText(), Integer.parseInt(portFiled.getText()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (socket != null && socket.isConnected()) {
                try {
                    transferRunnable = new FileTransferRunnable(readyFile, socket);
                    transferRunnable.setProcessListner(new ProcessListnerAdapter(){
                        @Override
                        public void updateProcess(Map<String, Object> processInfo) {
                            Object process = processInfo.get("process");
                            if(process != null && (process instanceof Integer)){
                                String processStr = ProcessUtil.strView((Integer) process);
                                processLable.setText(processStr);
                            }
                        }

                        @Override
                        public void end(Map<String, Object> processInfo) {
                            synchronized (readyFile) {
                                ipFiled.setEditable(true);
                                portFiled.setEditable(true);
                                send.setEnabled(true);
                                runing = false;
                            }
                        }
                    });
                    ipFiled.setEditable(false);
                    portFiled.setEditable(false);
                    send.setEnabled(false);
                    runing = true;
                    ThreadUtil.execute(transferRunnable);
                } catch (Exception runE) {
                    runE.printStackTrace();
                }
            }
            if (transferRunnable == null && socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                new InfoWindow("提示", "连接异常！");
            }
        } else {
            new InfoWindow("提示", "文件不存在或该文件为文件夹！");
        }
    }

    @Override
    protected boolean windowCanClose() {
        return !runing;
    }

    @Override
    protected void windowClose() {

    }
}
