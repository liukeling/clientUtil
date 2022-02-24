package org.example.util.file;

public class ReceiveHandleDto {
    private byte[] tmp = new byte[100];
    private int read;
    private boolean begin = false;
    private boolean end = false;
    private int beginIndex = 0;
    public ReceiveHandleDto(byte[] tmp, int read, boolean begin, boolean end, int beginIndex){
        this.tmp = tmp;
        this.read = read;
        this.begin = begin;
        this.end = end;
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

    public boolean isBegin() {
        return begin;
    }

    public void setBegin(boolean begin) {
        this.begin = begin;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }
}
