package org.example.util;

import java.io.IOException;

public class ByteUtils {
    private ByteUtils() {

    }

    public static final int COMPARE_END_FLAG = -1;
    public static final int COMPARE_FAILED_FLAG = 0;
    public static final int COMPARE_CONTINUE_FLAG = -2;

    /**
     * 标记比较 不匹配就返回，不会继续重新匹配
     *
     * @param source      标记
     * @param sourceIndex 标记比较的起始位置 - 包括这个位置
     * @param read        流来源 - 需要比对的 从0开始与标记的起始位置开始比较
     * @param readLength  流来源有效长度
     * @return 第一个参数是比较结果，第二个参数是流来源当前比较的位置
     * @throws IOException
     */
    public static int[] compareByte(byte[] source, int sourceIndex, byte[] read, int readLength) {
        if (sourceIndex < source.length && readLength > 0) {

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
        throw new RuntimeException("index failed");
    }

    /**
     * 从beginIndex开始截取到有效长度的最后
     *
     * @param beginIndex   从哪里开始截取，包括这个index
     * @param sourceLength source中有效长度
     * @param source
     * @return
     */
    public static byte[] subBytes(int beginIndex, int sourceLength, byte[] source) {
        if (sourceLength > beginIndex) {
            byte[] nextInfo = new byte[sourceLength - beginIndex];
            System.arraycopy(source, beginIndex, nextInfo, 0, sourceLength - beginIndex);
            return nextInfo;
        }
        return null;
    }

    /**
     * 拼接到一起
     *
     * @param b1
     * @param b2
     * @return
     */
    public static byte[] byteAdds(byte[] b1, byte[] b2) {
        if (b1 == null || b1.length == 0) {
            return b2;
        }
        if (b2 == null || b2.length == 0) {
            return b1;
        }
        byte[] info = new byte[b1.length + b2.length];
        System.arraycopy(b1, 0, info, 0, b1.length);
        System.arraycopy(b2, 0, info, b1.length, b2.length);
        return info;
    }
}
