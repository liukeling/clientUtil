package org.example.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class BaseRunnable implements Runnable {

    private boolean sendOK = false;
    private boolean receiveOk = false;
    /**
     * 发送ok消息 - 通知就绪
     * @param out
     * @throws IOException
     */
    protected void toSendOk(OutputStream out) throws IOException {
        out.write(Contants.beginTag);
        out.write(Contants.helloTag);
        out.write(Contants.endTag);
        out.flush();
        sendOK = true;
    }
    /**
     * 接收ok消息 - ok之后就开始
     * @param input
     * @throws IOException
     */
    protected String toReceiveOk(InputStream input) throws IOException {
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
            if(receiveOk){
                String allTag = beginTag+helloTag+endTag;
                return info.substring(info.indexOf(allTag) + allTag.length());
            }
        }
        return null;
    }

    /**
     * 握手是否ok
     * @return
     */
    protected final boolean helloOk(){
        return sendOK && receiveOk;
    }
}
