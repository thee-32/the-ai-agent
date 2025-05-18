package com.the.theaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebSearchToolTest {

    @Value("{search-api.api-key}")
    private String apiKey;


    @Test
    void searchWeb() {
        WebSearchTool webSearchTool = new WebSearchTool(apiKey);

        String message = webSearchTool.searchWeb("深圳");

        Assertions.assertNotNull(message);
    }

}