package com.the.theaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebScrapingToolTest {

    @Test
    void testWebScrapingTool() {
        WebScrapingTool webScrapingTool = new WebScrapingTool();
        String message = webScrapingTool.scrapeWebPage("www.github.com");
        Assertions.assertNotNull(message);
    }
}