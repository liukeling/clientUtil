package org.example.util.io;

import org.example.util.ByteUtils;
import org.example.util.ThreadUtil;
import org.example.util.listener.ProcessListner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class IOHandleThreadUtil {

    //队列
    private final byte[][] queue = new byte[30][];
    //锁
    private final Object lock = new Object();
    //存放的指针
    private int putIndex = 0;
    //读取的指针
    private int sendIndex = queue.length - 1;
    //队列是否满了
    private boolean full = false;
    //输入、输出流、是否运行
    private final InputStream ips;
    private final OutputStream ops;
    private boolean hasRun = true;

    //是否由工具关闭流
    private final boolean ipsClose;
    private final boolean opsClose;

    //是否处于等待
    private boolean sendWait = false;
    private boolean putWait = false;
    private long curSend = 0;
    private ProcessListner processListner;

    public IOHandleThreadUtil(InputStream ips, OutputStream ops, boolean ipsClose, boolean opsClose) {
        this.ips = ips;
        this.ops = ops;
        this.ipsClose = ipsClose;
        this.opsClose = opsClose;
    }

    private Runnable FRANS_PUT = new Runnable() {
        @Override
        public void run() {
            byte[] tmp = new byte[1024];
            int len;
            try {
                System.out.println("====in put===");
                while ((len = ips.read(tmp)) != -1) {
                    byte[] bytes = ByteUtils.subBytes(0, len, tmp);
                    synchronized (lock) {
                        putInfo(bytes);
                        if (!hasRun) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                synchronized (lock) {
                    hasRun = false;
                }
                if(ips != null && ipsClose) {
                    try {
                        ips.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("=====put end====");
            }
        }
    };
    private Runnable FRANS_SEND = new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("====in send===");
                while (canSend()) {
                    byte[] needSend = getSend();
                    if (needSend != null) {
                        ops.write(needSend);
                        ops.flush();
                        curSend += needSend.length;
                        if(processListner != null){
                            Map<String, Object> info = new HashMap<>(1);
                            info.put("info", curSend);
                            processListner.updateProcess(info);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                synchronized (lock) {
                    hasRun = false;
                }
                if(ops != null && opsClose) {
                    try {
                        ops.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (IOHandleThreadUtil.this){
                    IOHandleThreadUtil.this.notify();
                }
                if(processListner != null){
                    processListner.end(null);
                }
                System.out.println("=====send end====");
            }
        }
    };

    /**
     * 是否继续写
     *
     * @return
     */
    private boolean canSend() {
        synchronized (lock) {
            return hasRun || full;
        }
    }

    /**
     * 获取
     *
     * @return
     */
    private byte[] getSend() throws InterruptedException {
        synchronized (lock) {
            moveSend();
            byte[] cur;
            while((cur = queue[sendIndex]) == null){

                sendWait = true;
                lock.wait();
            }
            queue[sendIndex] = null;
            full = false;
            //通知可以继续放置了
            if(putWait) {
                putWait = false;
                lock.notify();
            }
            return cur;
        }
    }

    /**
     * 放置
     *
     * @param fileTmp
     * @throws InterruptedException
     */
    private void putInfo(byte[] fileTmp) throws InterruptedException {
        synchronized (lock) {
            if (fileTmp == null || fileTmp.length == 0) {
                return;
            }
            queue[putIndex] = fileTmp;
            movePut();
            full = queue[putIndex] != null;
            //通知可以继续发送
            if(sendWait) {
                sendWait = false;
                lock.notify();
            }
            if (full) {
                //满了，等待
                putWait = true;
                lock.wait();
            }
        }
    }

    /**
     * 放置的指针移动
     */
    private void movePut() {
        if (putIndex == queue.length - 1) {
            putIndex = 0;
        } else {
            putIndex++;
        }
    }

    /**
     * 获取的指针移动
     */
    private void moveSend() {
        if (sendIndex == queue.length - 1) {
            sendIndex = 0;
        } else {
            sendIndex ++;
        }
    }

    public void start() {
        start(null);
    }
    public void start(ProcessListner processListner) {
        this.processListner = processListner;
        if(processListner != null){
            processListner.begin(null);
        }
        ThreadUtil.execute(FRANS_PUT);
        ThreadUtil.execute(FRANS_SEND);
    }
    public void stop(){
        synchronized (lock) {
            hasRun = false;
        }
    }
}
