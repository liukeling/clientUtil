package org.example.util.io;

import org.example.util.ByteUtils;
import org.example.util.ThreadUtil;
import org.example.util.listener.ProcessListner;
import org.example.util.queue.HandleQueue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class IOHandleThreadUtil {

    private final HandleQueue<byte[]> queue = new HandleQueue<>(30);

    //输入、输出流、是否运行
    private final InputStream ips;
    private final OutputStream ops;
    private boolean hasRun = true;
    private Object lock = new Object();

    //是否由工具关闭流
    private final boolean ipsClose;
    private final boolean opsClose;

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
                while ((len = ips.read(tmp)) != -1) {
                    byte[] bytes = ByteUtils.subBytes(0, len, tmp);
                    queue.put(bytes);
                    synchronized (lock) {
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
            }
        }
    };
    private Runnable FRANS_SEND = new Runnable() {
        @Override
        public void run() {
            try {
                while (canSend()) {
                    byte[] needSend = queue.get();
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
            return hasRun || !queue.isEmpty();
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
