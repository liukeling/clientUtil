package org.example.util.handles;

import org.example.util.ByteUtils;
import org.example.util.Contants;
import org.example.util.file.ReceiveHandleDto;

/**
 * 文件接收处理
 * @author liukeling
 */
public class FileHandle extends BaseHandle {

    @Override
    public void handle(ReceiveHandleDto handleDto) {
        if (!handleDto.isTagOk()) {
            tagHandle(handleDto);
        }else{
            byte[] tag = handleDto.getTag();
            if (tag == Contants.beginTag) {
                handleDto.setTag(Contants.fileNameTag);
                handleDto.setTagOk(false);
                tagHandle(handleDto);
            } else if (tag == Contants.fileNameTag) {
                byte[] tmp = handleDto.getTmp();
                int read = handleDto.getRead();
                //后续的字符信息
                byte[] bytes = ByteUtils.subBytes(handleDto.getBeginIndex(), read, tmp);
                handleDto.setTmpInfo(ByteUtils.byteAdds(handleDto.getTmpInfo(), bytes));
                handleDto.setPreTag(tag);
                handleDto.setTag(Contants.reLineTag);
                handleDto.setTagOk(false);
                tagHandle(handleDto);
            }else if(tag == Contants.reLineTag){
                byte[] preTag = handleDto.getPreTag();
                if(preTag == null){
                    //todo
                }else{

                }
            }
        }
    }


}
