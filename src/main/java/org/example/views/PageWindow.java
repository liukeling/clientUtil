package org.example.views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PageWindow extends BaseWindow {
    private final GridLayout gridLayout = new GridLayout(3,3);
    private final Map<String, Component> componentMap;
    private java.util.List<Component> componentList;
    private final Button next = new Button("next"),pre = new Button("pre");
    private int curPage = 0;
    public PageWindow(String title, Map<String, Component> componentMap) {
        super(title);
        frame.setLayout(gridLayout);
        this.componentMap = componentMap;
        initComponentList();
        initPage(0);
        setLocation();
        initNextPre();
    }
    private void initComponentList(){
        Collection<Component> values = componentMap.values();
        componentList = new ArrayList<>(values.size());
        componentList.addAll(values);
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
                frame.add(i == 6 ? pre : next);
            }else{
                int index = i > 6 ? i - 1 : i;
                if(j < size) {
                    frame.add(componentList.get(index + (curPage * 7)),i);
                    j++;
                }else{
                    frame.add(new Container());
                }
            }
        }
        frame.validate();
    }
    private void initNextPre(){
        next.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                curPage ++;
                initPage(0);
            }
        });
        pre.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                curPage --;
                initPage(1);
            }
        });
    }

    @Override
    protected void windowClose() {

    }
}
