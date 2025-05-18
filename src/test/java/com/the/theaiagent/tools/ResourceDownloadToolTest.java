package com.the.theaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceDownloadToolTest {
    @Test
    void testResourceDownload() {
        ResourceDownloadTool downloadTool = new ResourceDownloadTool();
        String url = "https://zhaopin.jd.com/zhaopin/images/bg0.jpg";
        String fileName = "jd";
        String message = downloadTool.downloadResource(url, fileName);
        Assertions.assertNotNull(message);
    }
}