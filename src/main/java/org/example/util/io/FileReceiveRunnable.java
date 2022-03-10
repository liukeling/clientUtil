package org.example.util.io;

import org.example.util.Contants;
import org.example.util.handles.ReceiveHandleDto;
import org.example.util.handles.file.FileHandler;
import org.example.views.SyncList;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

/**
 * @author liukeling
 */
public class FileReceiveRunnable extends BaseIORunnable {
    private static final FileHandler fileHandler = new FileHandler();
    private static final int readSize = 100;
    private final Socket socket;
    private final SyncList list;
    private final String taskId = UUID.randomUUID().toString().replace("-","");

    public FileReceiveRunnable(Socket socket, SyncList list) throws IOException {
        super(socket.getInputStream(), socket.getOutputStream());
        this.socket = socket;
        this.list = list;
    }

    /**
     * 开始接收处理
     *
     * @param preInfos
     */
    @Override
    protected void beginReceive(byte[] preInfos) throws IOException {
        int read = 0;
        byte[] tmp;
        byte[] tag = Contants.beginTag;
        int beginIndex = 0;

        if (preInfos != null && preInfos.length > 0) {
            tmp = preInfos;
            read = tmp.length;
        } else {
            tmp = new byte[readSize];
        }
        ReceiveHandleDto handleDto = new ReceiveHandleDto(tmp, read, beginIndex, tag);
        if(list != null) {
            list.putInfo(taskId, "begin receive.....");
        }
        do {
            if (read > 0) {
                handleDto.setTmp(tmp);
                handleDto.setRead(read);
                fileHandler.handle(handleDto);
            }
        } while ((read = ips.read(tmp)) != -1);
    }

    @Override
    protected void finallyHandle() {
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(list != null) {
            list.removeInfo(taskId);
        }
    }
}
