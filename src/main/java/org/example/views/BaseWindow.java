package org.example.views;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class BaseWindow {
    protected final Frame frame;
    public BaseWindow(String title){
        frame = new Frame(title);
        frame.setVisible(true);
        frame.setSize(400,600);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                frame.dispose();
                windowClose();
            }
        });
    }
    protected void clean(){
        frame.removeAll();
        frame.repaint();
    }
    protected abstract void windowClose();
}
