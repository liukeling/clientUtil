package org.example.util.listener;

import java.util.Map;

public interface ProcessListner {
    void updateProcess(Map<String, Object> processInfo);
    void begin(Map<String, Object> processInfo);
    void end(Map<String, Object> processInfo);
}
