package org.example.util.handles.file.todos;

import org.example.util.enums.TagEnum;

public class TagTodoUtil {
    private TagTodoUtil(){

    }
    public static TagTodo getTodoByTag(TagEnum tagEnum){
        switch (tagEnum){
            case BEGIN:
                return new BeginTodo();
            case FILE_NAME:
                return new FaileNameTodo();
            case FILE_SIZE:
                return new FileSizeTodo();
            case RELINE:
                return new ReLineTodo();
        }
        return null;
    }
}
