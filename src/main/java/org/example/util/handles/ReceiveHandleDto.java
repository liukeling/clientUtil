package org.example.util.handles;

import java.util.HashMap;
import java.util.Map;

/**
 * tag匹对对象
 * @author liukeling
 */
public class ReceiveHandleDto {
    /**读取到的流信息*/
    private byte[] tmp = new byte[100];
    /**流的有效长度*/
    private int read;
    /**tag是否匹配完成*/
    private boolean tagOk = false;
    /**是否还有未处理完的字符*/
    private boolean hasLast = false;
    private boolean end = false;
    /**需要进行匹配的tag*/
    private byte[] tag;
    /**当前匹对的tag位置 - 当前位置还未匹对*/
    private int beginIndex = 0;
    /**内容缓存*/
    private final Map<String, Object> container = new HashMap<>();

    /**上一个tag标记*/
    private byte[] preTag;
    /**tag之间缓存的数据*/
    private byte[] tmpInfo;
    public ReceiveHandleDto(byte[] tmp, int read, int beginIndex, byte[] tag){
        this.tmp = tmp;
        this.read = read;
        this.tag = tag;
        this.beginIndex = beginIndex;
    }
    public byte[] getTmp() {
        return tmp;
    }

    public void setTmp(byte[] tmp) {
        this.tmp = tmp;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public boolean isTagOk() {
        return tagOk;
    }

    public void setTagOk(boolean tagOk) {
        this.tagOk = tagOk;
    }

    public byte[] getTag() {
        return tag;
    }

    public void setTag(byte[] tag) {
        this.tag = tag;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public byte[] getPreTag() {
        return preTag;
    }

    public void setPreTag(byte[] preTag) {
        this.preTag = preTag;
    }

    public byte[] getTmpInfo() {
        return tmpInfo;
    }

    public void setTmpInfo(byte[] tmpInfo) {
        this.tmpInfo = tmpInfo;
    }

    public boolean isHasLast() {
        return hasLast;
    }

    public void setHasLast(boolean hasLast) {
        this.hasLast = hasLast;
    }

    public Object getContainerInfo(String key){
        return container.get(key);
    }
    public void putContainerInfo(String key, Object info){
        container.put(key, info);
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
