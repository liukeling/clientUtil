package org.example;

import org.example.views.PageWindow;
import org.example.views.file.FileWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        java.util.List<Component> componentList = new ArrayList<>();
        JButton fileUtil = new JButton("文件传输");
        fileUtil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new FileWindow("文件传输");
            }
        });
        componentList.add(fileUtil);
        new PageWindow("工具箱", componentList);
    }
}
