package org.example.views.file;

import org.example.util.ThreadUtil;
import org.example.views.BaseWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FileWindow extends BaseWindow {

    private final GridLayout gridLayout = new GridLayout(5,5);
    private final JButton upper = new JButton("传输文件"), receive = new JButton("接收文件");
    public FileWindow(String title) {
        super(title);
        frame.setLayout(gridLayout);
        frame.setSize(500,300);
        clean();
        for (int i = 0; i < 25; i++) {
            if(i == 11 || i == 13){
                frame.add(i == 11 ? upper : receive);
            }else{
                frame.add(new Container());
            }
        }
        frame.validate();
        initButton();
    }

    @Override
    protected void windowClose() {
        ThreadUtil.exit();
    }

    private void initButton(){
        upper.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new FransferWindow("传输文件");
            }
        });
        receive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ReceiveWindow("接收文件");
            }
        });
    }
}
