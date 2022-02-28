package org.example.util.file;

/**
 * 读取信息处理对象
 * @author liukeling
 */
public class ReceiveHandleDto {
    private byte[] tmp = new byte[100];
    private int read;
    private boolean tagOk = false;
    private byte[] tag;
    private int beginIndex = 0;

    private byte[] preTag;
    private byte[] tmpInfo;
    public ReceiveHandleDto(byte[] tmp, int read, int beginIndex, byte[] tag, boolean tagOk){
        this.tmp = tmp;
        this.read = read;
        this.tag = tag;
        this.tagOk = tagOk;
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
}
