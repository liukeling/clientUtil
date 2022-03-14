package org.example.util.io;

import org.example.util.Contants;
import org.example.util.listener.ProcessListner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public abstract class BaseIORunnable implements Runnable {

    private boolean sendOK = false;
    private boolean receiveOk = false;
    protected final InputStream ips;
    protected final OutputStream ops;
    protected ProcessListner processListner;
    protected final String taskId = UUID.randomUUID().toString().replace("-","");

    public BaseIORunnable(InputStream ips, OutputStream ops) {
        this.ips = ips;
        this.ops = ops;
    }

    /**
     * 开始处理，先握手，ok之后再开始接收数据处理
     */
    @Override
    public final void run() {
        try {
            toSendOk(ops);
            String lastInfo = toReceiveOk(ips);
            if (helloOk()) {
                //wait receive.....
                ioHandle(lastInfo.getBytes());
            } else {
                System.out.println(Thread.currentThread().getName() + "  hello info failed.....");
            }
        } catch (Exception e) {
            exceptionHandle(e);
        } finally {
            if (ips != null) {
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ops != null) {
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            finallyHandle();
        }
    }

    /**
     * 数据处理
     *
     * @param lastInfo
     * @throws IOException
     */
    protected abstract void ioHandle(byte[] lastInfo) throws IOException;

    /**
     * 结束处理
     */
    protected abstract void finallyHandle();

    /**
     * 异常处理
     */
    protected void exceptionHandle(Exception e) {
        if (e != null) {
            e.printStackTrace();
        }
    }

    /**
     * 发送ok消息 - 通知就绪
     *
     * @param out
     * @throws IOException
     */
    private void toSendOk(OutputStream out) throws IOException {
        out.write(Contants.beginTag);
        out.write(Contants.helloTag);
        out.write(Contants.endTag);
        out.flush();
        sendOK = true;
    }

    /**
     * 接收ok消息 - ok之后就开始
     *
     * @param input
     * @throws IOException
     */
    private String toReceiveOk(InputStream input) throws IOException {
        byte[] tmp = new byte[20];
        int len;
        String info = "";
        String endTag = new String(Contants.endTag);
        String beginTag = new String(Contants.beginTag);
        String helloTag = new String(Contants.helloTag);
        while ((len = input.read(tmp)) != -1) {
            info += new String(tmp, 0, len);
            if (info.contains(endTag)) {
                break;
            }
        }
        if (info.contains(beginTag) && info.contains(endTag)) {
            String okInfo = info.substring(info.indexOf(beginTag) + beginTag.length(), info.indexOf(endTag));
            receiveOk = okInfo != null && okInfo.equals(helloTag);
            if (receiveOk) {
                String allTag = beginTag + helloTag + endTag;
                return info.substring(info.indexOf(allTag) + allTag.length());
            }
        }
        return null;
    }

    /**
     * 握手是否ok
     *
     * @return
     */
    protected final boolean helloOk() {
        return sendOK && receiveOk;
    }

    public ProcessListner getProcessListner() {
        return processListner;
    }

    public void setProcessListner(ProcessListner processListner) {
        this.processListner = processListner;
    }
}
