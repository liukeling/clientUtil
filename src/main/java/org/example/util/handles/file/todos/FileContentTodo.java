package org.example.util.handles.file.todos;

import org.example.util.ByteUtils;
import org.example.util.Contants;
import org.example.util.handles.ReceiveHandleDto;
import org.example.util.io.LocalFileUtil;

import java.io.File;
import java.io.IOException;

public class FileContentTodo implements TagTodo {
    private ThreadLocal<String> localFileName = new ThreadLocal<>();
    private ThreadLocal<Long> localFileSize = new ThreadLocal<>();
    private ThreadLocal<Long> localCurSize = new ThreadLocal<>();
    @Override
    public void tagEndTodo(ReceiveHandleDto handleDto) {
        //记录下此次的标记
        handleDto.setPreTag(handleDto.getTag());
        handleDto.setTag(Contants.endTag);
        handleDto.setTagOk(false);
        handleDto.setTmpInfo(null);
        Object fileName = handleDto.getContainerInfo(new String(Contants.fileNameTag));
        Object fileSize = handleDto.getContainerInfo(new String(Contants.fileSizeTag));
        if(fileName == null || fileSize == null){
            throw new RuntimeException("file info failed,can not to write file");
        }
        if(fileName instanceof File){
            String name = ((File) fileName).getName();
            localFileName.set(name);
        }else {
            localFileName.set(fileName.toString());
        }
        localFileSize.set(fileSize instanceof Long ? ((Long) fileSize) : Long.parseLong(fileSize.toString()));
    }

    @Override
    public Object tagInfoTodo(byte[] info) {
        String fileName = localFileName.get();
        Long fileSize = localFileSize.get();
        Long curSize = localCurSize.get();
        curSize = curSize == null ? 0 : curSize;
        try {
            if(info != null && info.length > 0) {
                byte[] bytes = info;
                if(info.length > fileSize - curSize){
                    int length = Long.valueOf(fileSize - curSize).intValue();
                    if(length <= 0){
                        bytes = null;
                    }else {
                        bytes = ByteUtils.subBytes(0, length, info);
                    }
                }
                if(bytes != null && bytes.length > 0) {
                    LocalFileUtil.write(fileName, bytes);
                    curSize = curSize + bytes.length;
                    localFileSize.set(curSize);
                }
                if(curSize.compareTo(fileSize) == 0){
                    return "end";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
