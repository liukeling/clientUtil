package org.example.util.handles.file.todos;

import org.example.util.enums.TagEnum;
import org.example.util.handles.ReceiveHandleDto;

public class ReLineTodo implements TagTodo {
    @Override
    public void tagEndTodo(ReceiveHandleDto handleDto) {
        byte[] preTag = handleDto.getPreTag();
        if (preTag == null) {
            System.out.println("=====处理文件信息失败，pre tag is null===");
        } else {
            byte[] tmpInfo = handleDto.getTmpInfo();
            TagEnum byTag = TagEnum.findByTag(preTag);
            TagTodo todoByTag = TagTodoUtil.getTodoByTag(byTag);
            todoByTag.tagInfoTodo(tmpInfo);
        }
    }

    @Override
    public void tagInfoTodo(byte[] info) {

    }
}
