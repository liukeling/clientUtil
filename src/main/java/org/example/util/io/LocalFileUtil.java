package org.example.util.io;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LocalFileUtil {
    private LocalFileUtil() {

    }

    private static final Map<String, File> TMP_LOCAL_FILES = new HashMap<>();
    private static String baseFilePath = "D:" + File.separator + "test";
    private static File baseDir = new File(baseFilePath);

    static {
        synchronized (TMP_LOCAL_FILES) {
            if (baseDir.exists()) {
                if (baseDir.isDirectory()) {
                    File[] files = baseDir.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            TMP_LOCAL_FILES.put(file.getName(), file);
                        }
                    }
                } else {
                    int i = 1;
                    do {
                        baseFilePath = baseFilePath + i;
                        baseDir = new File(baseFilePath);
                        i++;
                    } while (baseDir.exists());
                    baseDir.mkdir();
                }
            } else {
                baseDir.mkdir();
            }
        }
    }

    /**
     * 创建文件 - 已经存在就文件名后面加1
     *
     * @param fileName
     * @throws IOException
     */
    public static File createFile(String fileName) throws IOException {
        synchronized (TMP_LOCAL_FILES) {
            File file = TMP_LOCAL_FILES.get(fileName);
            if (file == null) {
                file = new File(baseFilePath + File.separator + fileName);
            }
            int i = 1;
            String name = fileName;
            String backInfo = "";
            if(fileName.contains(".")){
                name = fileName.substring(0, fileName.indexOf("."));
                backInfo = fileName.substring(fileName.indexOf("."));
            }
            String newName = "";
            while (file.exists()) {
                newName = name +"("+i+")";
                file = new File(baseFilePath + File.separator + newName+backInfo);
                i++;
            }
            file.createNewFile();
            TMP_LOCAL_FILES.put(newName+backInfo, file);
            return file;
        }
    }

    public static void write(String fileName, byte[] info) throws IOException {
        File file = null;
        synchronized (TMP_LOCAL_FILES) {
            file = TMP_LOCAL_FILES.get(fileName);
        }
        if(file != null) {
            synchronized (file) {
                if(file.exists() && file.isFile()){
                    FileOutputStream fos = new FileOutputStream(file,true);
                    fos.write(info);
                    fos.flush();
                    fos.close();
                }else{
                    System.out.println("====write failed, file is not exists or is not file====");
                }
            }
        }
    }
}
