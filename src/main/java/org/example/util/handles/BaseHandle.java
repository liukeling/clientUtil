package org.example.util.handles;

import org.example.util.ByteUtils;
import org.example.util.file.ReceiveHandleDto;

import java.io.IOException;

/**
 * @author liukeling
 */
public abstract class BaseHandle implements HandleInter{
    /**
     * 标记读取 - 不匹配的忽略处理。
     * @param handleDto
     * @throws IOException
     */
    public final void tagHandle(ReceiveHandleDto handleDto){
        //开始标记的判断
        int[] flags = ByteUtils.compareByte(handleDto.getTag(), handleDto.getBeginIndex(), handleDto.getTmp(), handleDto.getRead());
        byte[] nextByte = null;
        if (flags[0] == ByteUtils.COMPARE_FAILED_FLAG) {
            handleDto.setBeginIndex(0);
            int nextIndex = flags[1];
            nextIndex = nextIndex == handleDto.getBeginIndex() ? nextIndex + 1 : nextIndex;
            nextByte = ByteUtils.subBytes(nextIndex, handleDto.getRead(), handleDto.getTmp());
        } else if (flags[0] == ByteUtils.COMPARE_END_FLAG) {
            handleDto.setBeginIndex(0);
            handleDto.setTagOk(true);
            int nextIndex = flags[1] + 1;
            nextByte = ByteUtils.subBytes(nextIndex, handleDto.getRead(), handleDto.getTmp());
        } else if (flags[0] == ByteUtils.COMPARE_CONTINUE_FLAG) {
            handleDto.setBeginIndex( handleDto.getBeginIndex() + flags[1] + 1);
        }
        if(nextByte != null && nextByte.length > 0){
            handleDto.setTmp(nextByte);
            handleDto.setRead(nextByte.length);
            this.handle(handleDto);
        }
    }
}
