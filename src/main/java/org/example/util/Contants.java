package org.example.util;

public class Contants {
    private Contants(){

    }
    public static byte[] beginTag = "beginFlag".getBytes();
    public static byte[] endTag = "endFlag".getBytes();

    public static byte[] helloTag = "ok".getBytes();

    public static byte[] fileNameTag = "fileName:".getBytes();
    public static byte[] fileSizeTag = "fileSize:".getBytes();
    public static byte[] fileContentTag = "fileContent:".getBytes();

    public static byte[] reLineTag = "\r\n".getBytes();

    public static int port = 1818;
    public static int queueSize = 30;
    public static int readSize = 1024;
    public static String defaultIp = "localhost";
}
