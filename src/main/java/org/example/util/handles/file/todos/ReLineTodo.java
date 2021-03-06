package org.example.util.handles.file.todos;

import org.example.util.Contants;
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
            TagEnum preTagEnum = TagEnum.findByTag(preTag);
            TagTodo tagTodo = TagTodoUtil.getTodoByTag(preTagEnum);
            Object info = tagTodo.tagInfoTodo(tmpInfo);
            //tag内容存储
            if(info != null){
                handleDto.putContainerInfo(new String(preTag), info);
            }
            //下个标记 - preTag不重置
            if(preTag == Contants.fileNameTag){
                handleDto.setTag(Contants.fileSizeTag);
            }else if(preTag == Contants.fileSizeTag){
                handleDto.setTag(Contants.fileContentTag);
            }
            handleDto.setTagOk(false);
            handleDto.setTmpInfo(null);
        }
    }

    @Override
    public Object tagInfoTodo(byte[] info) {
        return null;
    }
}
