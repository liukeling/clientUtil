package org.example.util.handles.file;

import org.example.util.StringUtil;

/**
 * 文件内容信息
 */
public class FileContant {
    //文件名
    private static final ThreadLocal<String> localFileName = new ThreadLocal<>();
    //文件大小
    private static final ThreadLocal<Long> localFileSize = new ThreadLocal<>();
    //当前已经写入大小
    private static final ThreadLocal<Long> localCurSize = new ThreadLocal<>();

    public static void setFileName(String fileName) {
        localFileName.set(fileName);
    }

    public static void setFileSize(Long fileSize) {
        localFileSize.set(fileSize);
    }

    public static void setCurSize(Long curSize) {
        localCurSize.set(curSize);
    }

    public static String getFileName() {
        return localFileName.get();
    }

    public static Long getFileSize() {
        return localFileSize.get();
    }

    public static Long getCurSize() {
        return localCurSize.get();
    }
    public static String calCurProcess(){
        String fileName = localFileName.get();
        Long fileSize = localFileSize.get();
        Long curSize = localCurSize.get();
        if(StringUtil.isNotEmpty(fileName)) {
            String process = fileSize == null || fileSize.compareTo(0L) == 0 ? "0%"
                    :(curSize == null || curSize.compareTo(0L) == 0 ? "0%" : (curSize*100/fileSize)+"%");
            String info = "receive:"+fileName+" process:"+(process);
            return info;
        }
        return null;
    }
}
