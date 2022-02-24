package org.example.util;

import java.io.IOException;

public class ByteUtils {
    private ByteUtils(){

    }
    public static final int COMPARE_END_FLAG = -1;
    public static final int COMPARE_FAILED_FLAG = 0;
    public static final int COMPARE_CONTINUE_FLAG = -2;
    /**
     * 标记比较
     * @param source
     * @param sourceIndex
     * @param read
     * @param readLength
     * @return
     * @throws IOException
     */
    public static int[] compareByte(byte[] source, int sourceIndex, byte[] read, int readLength) throws IOException{
        if(sourceIndex < source.length && readLength > 0) {

            for (int i = 0; sourceIndex + i < source.length && i < readLength; i++) {
                byte s = source[sourceIndex + i];
                byte r = read[i];
                if (s == r) {
                    if (sourceIndex + i == source.length - 1) {
                        return new int[]{COMPARE_END_FLAG, i};
                    }
                    if (i == readLength - 1) {
                        return new int[]{COMPARE_CONTINUE_FLAG, i};
                    }
                } else {
                    return new int[]{COMPARE_FAILED_FLAG, i};
                }
            }
        }
        throw new IOException("index failed");
    }
    /**
     *
     * @param beginIndex
     * @param sourceLength source中有效长度
     * @param source
     * @return
     */
    public static byte[] subBytes(int beginIndex, int sourceLength, byte[] source){
        if(sourceLength > beginIndex) {
            byte[] nextInfo = new byte[sourceLength - beginIndex];
            System.arraycopy(source, beginIndex, nextInfo, 0,sourceLength - beginIndex);
            return nextInfo;
        }
        return null;
    }
}
