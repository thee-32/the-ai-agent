package com.the.theaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
//public class OllamaAiInvoke implements CommandLineRunner {
//    @Resource
//    private ChatModel ollamaChatModel;
//    @Override
//    public void run(String... args) throws Exception {
//        AssistantMessage output = ollamaChatModel.call(new Prompt("你好，我是the"))
//                .getResult()
//                .getOutput();
//        System.out.println(output.getText());
//
//    }
//}
