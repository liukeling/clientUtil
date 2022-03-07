package org.example.util.handles.file.todos;

import org.example.util.ByteUtils;
import org.example.util.Contants;
import org.example.util.handles.ReceiveHandleDto;

public class FaileNameTodo implements TagTodo {
    @Override
    public void tagEndTodo(ReceiveHandleDto handleDto) {
        //记录下此次的标记 - reline标记里用
        handleDto.setPreTag(handleDto.getTag());
        handleDto.setTag(Contants.reLineTag);
        handleDto.setTagOk(false);
        handleDto.setTmpInfo(null);
    }

    @Override
    public void tagInfoTodo(byte[] info) {

    }
}
