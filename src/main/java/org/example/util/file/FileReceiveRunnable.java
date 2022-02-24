package org.example.util.file;

import org.example.util.ByteUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FileReceiveRunnable extends BaseRunnable {
    private final Socket socket;
    public FileReceiveRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("=====begin receive===" + Thread.currentThread().getName());
        try {
            InputStream input = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            toSendOk(out);
            String lastInfo = toReceiveOk(input);
            if (helloOk()) {
                //wait receive.....
                System.out.println("=========lastInfo:"+lastInfo);
                beginReceive(input, out, lastInfo.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("=====exit receive====" + Thread.currentThread().getName());
    }

    private void beginReceive(InputStream input, OutputStream out, byte[] preInfos) {
        System.out.println("====begin handle receive====" + Thread.currentThread().getName());
        try {
            int read = 0;
            byte[] tmp = new byte[100];
            boolean begin = false;
            boolean end = false;
            int beginIndex = 0;

            if(preInfos != null && preInfos.length > 0){
                tmp = preInfos.length > 100 ? new byte[preInfos.length] : tmp;
                for (int i = 0; i < preInfos.length; i++) {
                    tmp[i] = preInfos[i];
                }
                read = preInfos.length;
            }

            do{
                if(read > 0) {
                    ReceiveHandleDto handleDto = new ReceiveHandleDto(tmp, read, begin, end, beginIndex);
                    handle(handleDto);
                    begin = handleDto.isBegin();
                    end = handleDto.isEnd();
                    beginIndex = handleDto.getBeginIndex();
                }
            }while ((read = input.read(tmp)) != -1);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 接收开始
     * @param handleDto
     * @throws IOException
     */
    private void handle(ReceiveHandleDto handleDto) throws IOException{
        if (!handleDto.isBegin()) {
            tagHandle(handleDto, Contants.beginTag);
        }else{
            tagHandle(handleDto, Contants.fileNameTag);
        }
    }

    /**
     * 开始标记判断
     * @param handleDto
     * @throws IOException
     */
    private void tagHandle(ReceiveHandleDto handleDto, byte[] tag) throws IOException{
        //开始标记的判断
        int[] flags = ByteUtils.compareByte(tag, handleDto.getBeginIndex(), handleDto.getTmp(), handleDto.getRead());
        byte[] nextByte = null;
        if (flags[0] == ByteUtils.COMPARE_FAILED_FLAG) {
            handleDto.setBeginIndex(0);
            int nextIndex = flags[1];
            nextIndex = nextIndex == handleDto.getBeginIndex() ? nextIndex + 1 : nextIndex;
            nextByte = ByteUtils.subBytes(nextIndex, handleDto.getRead(), handleDto.getTmp());
        } else if (flags[0] == ByteUtils.COMPARE_END_FLAG) {
            handleDto.setBeginIndex(0);
            handleDto.setBegin(true);
            int nextIndex = flags[1] + 1;
            nextByte = ByteUtils.subBytes(nextIndex, handleDto.getRead(), handleDto.getTmp());
        } else if (flags[0] == ByteUtils.COMPARE_CONTINUE_FLAG) {
            handleDto.setBeginIndex( handleDto.getBeginIndex() + flags[1] + 1);
        }
        if(nextByte != null && nextByte.length > 0){
            handleDto.setTmp(nextByte);
            handleDto.setRead(nextByte.length);
            handle(handleDto);
        }
    }


}
