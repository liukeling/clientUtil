package org.example.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 10, 6, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(40));
    private ThreadUtil(){

    }
    public static void execute(Runnable runnable){
        poolExecutor.execute(runnable);
    }
    public static void exit(){
        poolExecutor.shutdown();
    }
}
