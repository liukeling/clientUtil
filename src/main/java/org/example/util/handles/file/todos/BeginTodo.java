package org.example.util.handles.file.todos;

import org.example.util.Contants;
import org.example.util.handles.ReceiveHandleDto;

public class BeginTodo implements TagTodo{

    /**
     * 开始标记结束后的处理
     * @param handleDto
     */
    @Override
    public void tagEndTodo(ReceiveHandleDto handleDto) {
        //读取文件名标记
        handleDto.setPreTag(handleDto.getTag());
        handleDto.setTag(Contants.fileNameTag);
        handleDto.setTagOk(false);
        handleDto.setTmpInfo(null);
    }

    @Override
    public Object tagInfoTodo(byte[] info) {
        return null;
    }
}
