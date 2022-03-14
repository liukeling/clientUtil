package org.example.util.handles;

import org.example.util.ByteUtils;

import java.io.IOException;

/**
 * @author liukeling
 */
public abstract class BaseHandler<T extends ReceiveHandleDto> implements HandleInter<T> {
    /**
     * 标记读取 - 不匹配的记录添加到tmpInfo内
     *
     * @param handleDto
     * @throws IOException
     */
    protected void tagHandle(T handleDto) {
        byte[] tag = handleDto.getTag();
        int beginIndex = handleDto.getBeginIndex();
        byte[] tmp = handleDto.getTmp();
        int read = handleDto.getRead();
        //流对比
        int[] flags = ByteUtils.compareByte(tag, beginIndex, tmp, read);
        byte[] nextByte = null;
        if (flags[0] == ByteUtils.COMPARE_FAILED_FLAG) {
            handleDto.setBeginIndex(0);
            {
                //记录未读取处理的部分
                int nextIndex = flags[1] + 1;
                nextByte = ByteUtils.subBytes(nextIndex, read, tmp);
            }
            {
                //将该匹配段记录到tmpInfo内
                int tmpLength = beginIndex + flags[1];
                byte[] needTmp;
                if(tmpLength > 0) {
                    byte[] preBytes = ByteUtils.subBytes(0, tmpLength, tag);
                    needTmp = ByteUtils.byteAdds(preBytes, new byte[]{tmp[flags[1]]});
                }else{
                    needTmp = new byte[]{tmp[flags[1]]};
                }
                byte[] tmpInfo = ByteUtils.byteAdds(handleDto.getTmpInfo(), needTmp);
                handleDto.setTmpInfo(tmpInfo);
            }
        } else if (flags[0] == ByteUtils.COMPARE_END_FLAG) {
            handleDto.setBeginIndex(0);
            handleDto.setTagOk(true);
            {
                //记录未读取处理的部分
                int nextIndex = flags[1] + 1;
                nextByte = ByteUtils.subBytes(nextIndex, read, tmp);
            }
        } else if (flags[0] == ByteUtils.COMPARE_CONTINUE_FLAG) {
            handleDto.setBeginIndex(handleDto.getBeginIndex() + flags[1] + 1);
        }

        //标记还有未处理完的流信息
        boolean hasLast = nextByte != null && nextByte.length > 0;
        handleDto.setHasLast(hasLast);
        if (hasLast) {
            handleDto.setTmp(nextByte);
            handleDto.setRead(nextByte.length);
        }
    }
}
