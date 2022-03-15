package org.example.views.file;

import org.example.util.ThreadUtil;
import org.example.views.BaseWindow;
import org.example.views.InfoWindow;
import org.example.views.SyncList;
import org.example.views.file.runnable.ReceiveServerRunnable;

import javax.swing.*;
import java.awt.*;

/**
 * 接收文件
 */
public class ReceiveWindow extends BaseWindow {
    private JTextField infoShow = new JTextField("padding...");
    private SyncList list = new SyncList();
    private BorderLayout borderLayout = new BorderLayout();

    private ReceiveServerRunnable serverRun = new ReceiveServerRunnable(infoShow, list);

    public ReceiveWindow(String title) {
        super(title);
        frame.setSize(500, 400);
        frame.setLayout(borderLayout);
        clean();
        frame.add(infoShow, BorderLayout.NORTH);
        frame.add(list.getView(), BorderLayout.CENTER);
        infoShow.setEditable(false);
        frame.validate();
        setLocation();
        initReceive();
    }

    @Override
    protected void windowClose() {
        serverRun.closeServer();
    }

    @Override
    protected boolean windowCanClose() {
        boolean taskOK = list.getItemCount() == 0;
        if (!taskOK) {
            new InfoWindow("info", "please wait,task has run...");
        }
        return taskOK;
    }

    private void initReceive() {
        ThreadUtil.execute(serverRun);
    }
}
