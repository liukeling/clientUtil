package org.example.util.handles;

import org.example.util.file.ReceiveHandleDto;

/**
 * @author liukeling
 */
public interface HandleInter {
    /**
     * 读取的流处理
     * @param handleDto
     */
    void handle(ReceiveHandleDto handleDto);
}
