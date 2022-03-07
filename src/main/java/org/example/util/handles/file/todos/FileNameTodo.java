package org.example.util.handles.file.todos;

import org.example.util.ByteUtils;
import org.example.util.Contants;
import org.example.util.handles.ReceiveHandleDto;
import org.example.util.io.LocalFileUtil;

import java.io.File;

public class FileNameTodo implements TagTodo {
    @Override
    public void tagEndTodo(ReceiveHandleDto handleDto) {
        //记录下此次的标记 - reline标记里用
        handleDto.setPreTag(handleDto.getTag());
        handleDto.setTag(Contants.reLineTag);
        handleDto.setTagOk(false);
        handleDto.setTmpInfo(null);
    }

    @Override
    public Object tagInfoTodo(byte[] info) {
        String fileName = new String(info);
        try {
            return LocalFileUtil.createFile(fileName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
