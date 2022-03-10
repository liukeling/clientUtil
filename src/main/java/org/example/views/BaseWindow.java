package org.example.views;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class BaseWindow {
    protected final Frame frame;
    protected final WindowListener windowClosing = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            if(windowCanClose()) {
                frame.dispose();
                windowClose();
            }
        }
    };
    public BaseWindow(String title){
        frame = new Frame(title);
        frame.setVisible(true);
        frame.setSize(400,600);
        frame.addWindowListener(windowClosing);
    }
    protected void clean(){
        frame.removeAll();
        frame.repaint();
    }
    protected abstract void windowClose();

    protected boolean windowCanClose(){
        return true;
    }
}
