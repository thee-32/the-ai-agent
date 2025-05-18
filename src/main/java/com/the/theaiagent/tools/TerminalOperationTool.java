package com.the.theaiagent.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 自定义终端操作工具
 */
public class TerminalOperationTool {

    @Tool(description = "Execute a command in the terminal")
    public String executeCommand(@ToolParam(description = "Command to execute in the terminal") String command) {
        StringBuilder output = new StringBuilder();

        try{
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);

            Process process = pb.start();

            try(BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            int exitCodde = process.waitFor();
            if(exitCodde != 0){
                output.append("Command execution failed with exit code: ").append(exitCodde);
            }
        } catch (IOException |InterruptedException e) {
            output.append("Command execution failed with exit code: ").append(e.getMessage());
        }

        return output.toString();
    }
}
