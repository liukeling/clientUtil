package org.example.util.handles.file.todos;

import org.example.util.Contants;
import org.example.util.enums.TagEnum;
import org.example.util.handles.ReceiveHandleDto;
import org.example.util.handles.file.FileContant;
import org.example.util.io.LocalFileUtil;

import java.util.HashMap;
import java.util.Map;

public class EndTodo implements TagTodo {
    @Override
    public void tagEndTodo(ReceiveHandleDto handleDto) {
        byte[] preTag = handleDto.getPreTag();
        if (preTag == null) {
            System.out.println("=====处理文件信息失败，pre tag is null===");
        } else {
            byte[] tmpInfo = handleDto.getTmpInfo();
            TagEnum preTagEnum = TagEnum.findByTag(preTag);
            TagTodo tagTodo = TagTodoUtil.getTodoByTag(preTagEnum);
            Object info = tagTodo.tagInfoTodo(tmpInfo);
            if(info != null) {
                handleDto.setTagOk(true);
                handleDto.setHasLast(false);
                handleDto.setEnd(true);
                String fileName = FileContant.getFileName();
                LocalFileUtil.stopWrite(fileName);
            }else{
                //当前读取到的结束标记不是真的结束了，只是碰巧
                tagTodo.tagInfoTodo(handleDto.getTag());
                handleDto.setTag(handleDto.getTag());
                handleDto.setTagOk(false);
                handleDto.setTmpInfo(null);
            }
        }
    }

    @Override
    public Object tagInfoTodo(byte[] info) {
        return null;
    }
}
