package org.example.util.handles.file.todos;

import org.example.util.handles.ReceiveHandleDto;

public interface TagTodo {
    /**
     * 标记读取完之后
     * @param handleDto
     */
    void tagEndTodo(ReceiveHandleDto handleDto);

    /**
     * 标记内容处理
     * @param info  内容
     */
    Object tagInfoTodo(byte[] info);
}
