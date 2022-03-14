package org.example;

import org.example.views.file.FileWindow;
import org.example.views.file.FransferInfoWindow;
import org.example.views.file.FransferWindow;
import org.example.views.file.ReceiveWindow;

import java.io.File;

public class App {
    public static void main(String[] args) {
        new ReceiveWindow("test");
        new FransferInfoWindow(new File("D:\\2eeebce0ba13d93160a42c436e9cc69a.jpeg"));
    }
}
