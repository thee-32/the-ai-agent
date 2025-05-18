package com.the.theaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TerminalOperationToolTest {
    @Test
    public void testTerminalOperation() {
        TerminalOperationTool tool = new TerminalOperationTool();
        String command = "java -version";
        String message = tool.executeCommand(command);
        Assertions.assertNotNull(message);
    }
}