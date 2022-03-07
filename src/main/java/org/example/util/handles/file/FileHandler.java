package org.example.util.handles.file;

import org.example.util.enums.TagEnum;
import org.example.util.handles.BaseHandler;
import org.example.util.handles.ReceiveHandleDto;
import org.example.util.handles.file.todos.TagTodoUtil;
import org.example.util.handles.file.todos.TagTodo;

/**
 * 文件接收处理
 *
 * @author liukeling
 */
public class FileHandler extends BaseHandler {

    @Override
    public void handle(ReceiveHandleDto handleDto) {
        do{
            if(!handleDto.isTagOk()) {
                tagHandle(handleDto);
            }
            if(handleDto.isTagOk()){
                byte[] tag = handleDto.getTag();
                TagEnum tagEnum = TagEnum.findByTag(tag);
                //标记结束后的处理
                TagTodo todoByTag = TagTodoUtil.getTodoByTag(tagEnum);
                if(todoByTag != null) {
                    todoByTag.tagEndTodo(handleDto);
                }else{
                    System.out.println("=====unknow tag:"+new String(tag)+" do not handle...===");
                    handleDto.setHasLast(false);
                }
            }
        }while(handleDto.isHasLast());

    }
}
