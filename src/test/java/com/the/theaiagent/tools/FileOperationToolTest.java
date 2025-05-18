package com.the.theaiagent.tools;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileOperationToolTest {


    @Test
    void testReadFile() {
        FileOperationTool tool = new FileOperationTool();
        String message = tool.readFile("./test.txt");
        System.out.println(message);
    }

    @Test
    void testWriteFile() {
        FileOperationTool tool = new FileOperationTool();
        String message = tool.writeFile("./test.txt", "test");
        System.out.println(message);
    }

}