package org.example.util.handles;

/**
 * @author liukeling
 */
public interface HandleInter<T extends ReceiveHandleDto> {
    /**
     * 读取的流处理
     * @param handleDto
     */
    void handle(T handleDto);
}
