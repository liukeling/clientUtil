package org.example.views;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SyncList {
    List view = new List();
    private final Object lock = new Object();
    Map<String, String> infos = new HashMap<>();
    public String putInfo(String taskId, String info){
        synchronized (lock) {
            String preInfo = infos.put(taskId, info);
            if(preInfo != null){
                view.remove(preInfo);
            }
            view.add(info);
            return preInfo;
        }
    }
    public String removeInfo(String taskId){
        synchronized (lock) {
            String preInfo = infos.remove(taskId);
            view.remove(preInfo);
            return preInfo;
        }
    }
    public int getItemCount(){
        synchronized (lock){
            return view.getItemCount();
        }
    }
    public List getView(){
        return view;
    }
}
