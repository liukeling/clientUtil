package org.example.views;

import javax.swing.*;
import java.awt.*;

public class InfoWindow extends BaseWindow {

    public InfoWindow(String title, String info) {
        super(title);
        frame.setSize(200,100);
        frame.add(new JTextField(info));
        setLocation();
    }

    @Override
    protected void windowClose() {

    }
}
