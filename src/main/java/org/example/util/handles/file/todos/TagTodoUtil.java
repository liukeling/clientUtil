package org.example.util.handles.file.todos;

import org.example.util.enums.TagEnum;

import java.util.HashMap;
import java.util.Map;

public class TagTodoUtil {
    private TagTodoUtil(){

    }
    private static final Map<TagEnum, TagTodo> curInfo = new HashMap<>(TagEnum.values().length);
    public static TagTodo getTodoByTag(TagEnum tagEnum){
        synchronized (curInfo) {
            TagTodo tagTodo = curInfo.get(tagEnum);
            if(tagTodo == null) {
                switch (tagEnum) {
                    case BEGIN:
                        tagTodo = new BeginTodo();
                        break;
                    case FILE_NAME:
                        tagTodo = new FileNameTodo();
                        break;
                    case FILE_SIZE:
                        tagTodo = new FileSizeTodo();
                        break;
                    case RELINE:
                        tagTodo = new ReLineTodo();
                        break;
                    case FILE_CONTENT:
                        tagTodo = new FileContentTodo();
                        break;
                    case END:
                        tagTodo = new EndTodo();
                }
                if(tagTodo != null){
                    curInfo.put(tagEnum, tagTodo);
                }
                return tagTodo;
            }else {
                return tagTodo;
            }
        }
    }
}
