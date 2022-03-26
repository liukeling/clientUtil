package org.example.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PageWindow extends BaseWindow {
    private final GridLayout gridLayout = new GridLayout(3,3);
    private java.util.List<Component> componentList;
    private final JButton next = new JButton("下一页"),pre = new JButton("上一页");
    private int curPage = 0;
    public PageWindow(String title, List<Component> componentList) {
        super(title);
        frame.setSize(500,400);
        frame.setLayout(gridLayout);
        this.componentList = componentList;
        initPage(0);
        setLocation();
        initNextPre();
    }
    private void initPage(int pageBackType){
        curPage = curPage < 0 ? 0 : curPage;
        int size = componentList.size() - curPage*7;
        size = size > 7 ? 7 : size;
        if(size <= 0){
            if(pageBackType == 0) {
                if(curPage > 0) {
                    curPage--;
                }
            }else{
                curPage ++;
            }
            return;
        }
        clean();
        for (int i = 0,j = 0; i < 9; i++) {
            if(i == 6 || i == 8){
                addView(i == 6 ? pre : next, -1);
            }else{
                int index = i > 6 ? i - 1 : i;
                if(j < size) {
                    addView(componentList.get(index + (curPage * 7)),i);
                    j++;
                }else{
                    addView(new Container(), -1);
                }
            }
        }
        frame.validate();
    }
    private void initNextPre(){
        next.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curPage ++;
                initPage(0);
            }
        });
        pre.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curPage --;
                initPage(1);
            }
        });
    }
    private void addView(Component view, int index){
        Container container = new Container();
        container.setLayout(new BorderLayout());
        container.add(view,BorderLayout.CENTER);
        frame.add(container, index);
    }
    @Override
    protected void windowClose() {

    }
}
