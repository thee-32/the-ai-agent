package com.the.theaiagent.app;

import cn.hutool.core.lang.UUID;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class LoveAppTest {
    
    @Resource
    private LoveApp loveApp;

    @Test
    void testChat(){
        String chatId = UUID.randomUUID().toString();

        //第一轮
        String message = "hello,I'm the";
        String answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);

        //第二轮
//        message = "hello,I want  Lucy to love me";
//        answer = loveApp.doChat(message, chatId);
//        Assertions.assertNotNull(answer);
//
//        //第三轮
//        message = "Do you remember my lover?";
//        answer = loveApp.doChat(message, chatId);
//        Assertions.assertNotNull(answer);
    }
    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是程序员鱼皮，我想让另一半（编程导航）更爱我，但我不知道该怎么做";
        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveReport);
    }

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();

        String message = "我已经结婚了，但是婚后关系不太亲密，怎么办？";
        String answer = loveApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }

    
}