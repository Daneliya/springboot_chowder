package com.xxl.sse.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xxl
 * @date 2024/10/27 16:43
 */
@Slf4j
@Data
public class UploadStatus {

    private long bytesRead;
    private long contentLength;
    private int items;

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
        log.info("Upload progress: {}%", calculateProgress());
    }

    private int calculateProgress() {
        if (contentLength == 0) {
            return 0;
        }
        return (int) ((bytesRead * 100) / contentLength);
    }

}