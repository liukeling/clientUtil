package org.example.util.handles.file;

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
}
