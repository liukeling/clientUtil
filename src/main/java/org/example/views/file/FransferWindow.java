package org.example.views.file;

import org.example.util.StringUtil;
import org.example.util.io.LocalFileUtil;
import org.example.views.BaseWindow;
import org.example.views.InfoWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * 传输
 */
public class FransferWindow extends BaseWindow {
    private BorderLayout borderLayout = new BorderLayout();
    private JTextField textField = new JTextField(null, null, 45);
    private JLabel info = new JLabel("文件夹路径：");
    private JButton button = new JButton("搜索");
    private List list = new List();
    private File[] files = null;

    public FransferWindow(String title) {
        super(title);
        initView();
    }

    @Override
    protected void windowClose() {

    }

    private void initView() {
        frame.setSize(700, 500);
        clean();
        frame.setLayout(borderLayout);
        JPanel jPanel = new JPanel();
        jPanel.add(info);
        jPanel.add(textField);
        jPanel.add(button);
        frame.add(jPanel, BorderLayout.NORTH);
        frame.add(list, BorderLayout.CENTER);
        frame.validate();

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String path = textField.getText();
                files = LocalFileUtil.getPathFiles(path);
                listShow();
            }
        });
        list.addMouseListener(new MouseAdapter() {
            private int count = 0;
            private Long preTime = null;
            private final int COUNT_CLICK = 2;
            private Integer preIndex = null;
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Long curTime = System.currentTimeMillis();
                if(preTime != null && curTime - preTime <= 1000
                        && preIndex != null && preIndex.intValue() == list.getSelectedIndex()) {
                    count++;
                    if (count == COUNT_CLICK) {
                        count = 0;
                        int index = list.getSelectedIndex();
                        openFile(files[index]);
                    }
                }else{
                    count = 1;
                }
                preTime = System.currentTimeMillis();
                preIndex = list.getSelectedIndex();
            }
        });
    }
    private void listShow(){
        list.removeAll();
        if (files != null && files.length > 0) {
            for (File file : files) {
                list.add((file.isFile() ? "文件：" : "文件夹：") + file.getName());
            }
        }
    }
    private void openFile(File file){
        if(file.isDirectory()){
            textField.setText(file.getPath());
            files = file.listFiles();
            listShow();
        }else{
            new FransferInfoWindow(file);
        }
    }
}
