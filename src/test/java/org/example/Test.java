package org.example;

import org.example.util.ThreadUtil;
import org.example.util.io.IOHandleThreadUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Test {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\test.sql");
        FileInputStream fis = new FileInputStream(file);
        File newFile = new File("D:\\test\\test.sql");
        newFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(newFile);
        IOHandleThreadUtil testIO = new IOHandleThreadUtil(fis, fos,true,true);
        testIO.start();
        ThreadUtil.exit();
    }
}
