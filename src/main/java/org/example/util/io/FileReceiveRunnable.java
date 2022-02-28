package org.example.util.io;

import org.example.util.ByteUtils;
import org.example.util.Contants;
import org.example.util.file.ReceiveHandleDto;
import org.example.util.handles.FileHandle;

import java.io.IOException;
import java.net.Socket;

/**
 * @author liukeling
 */
public class FileReceiveRunnable extends BaseIORunnable {
    private static final FileHandle fileHandle = new FileHandle();

    public FileReceiveRunnable(Socket socket) throws IOException {
        super(socket.getInputStream(), socket.getOutputStream());
    }
    /**
     * 开始接收处理
     * @param preInfos
     */
    @Override
    protected void beginReceive(byte[] preInfos) {
        System.out.println("====begin handle receive====" + Thread.currentThread().getName());
        try {
            int read = 0;
            byte[] tmp = new byte[100];

            boolean tagOk = false;
            byte[] tag = Contants.beginTag;
            int beginIndex = 0;

            if(preInfos != null && preInfos.length > 0){
                tmp = preInfos.length > 100 ? new byte[preInfos.length] : tmp;
                for (int i = 0; i < preInfos.length; i++) {
                    tmp[i] = preInfos[i];
                }
                read = preInfos.length;
            }

            ReceiveHandleDto handleDto = new ReceiveHandleDto(tmp, read, beginIndex, tag, tagOk);
            do{
                if(read > 0) {
                    handleDto.setTmp(tmp);
                    handleDto.setRead(read);
                    fileHandle.handle(handleDto);
                }
            }while ((read = ips.read(tmp)) != -1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
