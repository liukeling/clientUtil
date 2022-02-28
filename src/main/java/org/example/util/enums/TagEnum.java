package org.example.util.enums;

import org.example.util.ByteUtils;
import org.example.util.Contants;

/**
 * 标记枚举
 */
public enum TagEnum {
    BEGIN(Contants.beginTag, "开始标记"),
    END(Contants.endTag, "结束标记"),
    HELLO(Contants.helloTag, "ok标记"),
    FILE_NAME(Contants.fileNameTag, "文件名标记"),
    FILE_SIZE(Contants.fileSizeTag, "文件大小标记"),
    FILE_CONTENT(Contants.fileContentTag, "文件内容、流标记"),
    RELINE(Contants.reLineTag, "换行标记");
    private byte[] tag;
    private String desc;

    TagEnum(byte[] tag, String desc) {
        this.tag = tag;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public byte[] getTag() {
        return tag;
    }

    public static TagEnum findByTag(byte[] tag) {
        if (tag != null) {
            for (TagEnum value : values()) {
                int[] infos = ByteUtils.compareByte(value.tag, 0, tag, tag.length);
                if(infos[0] == ByteUtils.COMPARE_END_FLAG){
                    return value;
                }
            }
        }
        return null;
    }
}
