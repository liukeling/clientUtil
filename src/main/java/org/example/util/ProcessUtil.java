package org.example.util;

public class ProcessUtil {
    private ProcessUtil(){

    }
    public static String strView(int process){
        String processStr = "|s%|  "+process+"%";
        String view = "";
        for (int i = 1; i <= 10; i++) {
            view += process >= i*10 ? "=" : "_";
        }
        return processStr.replace("s%", view);
    }
}
