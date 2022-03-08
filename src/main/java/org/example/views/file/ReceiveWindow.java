package org.example.views.file;

import org.example.util.ThreadUtil;
import org.example.views.BaseWindow;
import org.example.views.file.runnable.ReceiveServerRunnable;

import javax.swing.*;
import java.awt.*;

/**
 * 接收文件
 */
public class ReceiveWindow extends BaseWindow {
    private JTextField infoShow = new JTextField("准备中...");
    private List list = new List();
    private GridLayout gridLayout = new GridLayout(4,1);
    private ReceiveServerRunnable serverRun = new ReceiveServerRunnable(infoShow);
    public ReceiveWindow(String title) {
        super(title);
        frame.setSize(500,400);
        frame.setLayout(gridLayout);
        clean();
        frame.add(infoShow);
        frame.add(list);
        infoShow.setEditable(false);
        frame.validate();
        initReceive();
    }

    @Override
    protected void windowClose() {
        serverRun.closeServer();
    }

    private void initReceive(){
        ThreadUtil.execute(serverRun);
    }
}
