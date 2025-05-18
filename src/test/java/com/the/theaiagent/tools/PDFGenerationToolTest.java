package com.the.theaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PDFGenerationToolTest {

    @Test
    public void testGeneratePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String filename = "test.pdf";
        String content = "This is a test pdf";

        String message = tool.generatePDF(filename, content);

        Assertions.assertNotNull(message);

    }
}