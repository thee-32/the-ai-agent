package com.the.theaiagent.app;


import com.the.theaiagent.advisor.MyLoggerAdvisor;
import com.the.theaiagent.chatmemory.FileBasedChatMemory;

import com.the.theaiagent.rag.QueryRewriter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class LoveApp {

    @Resource
    private VectorStore loveAppVectorStore;

    @Resource
    private Advisor loveAppRagCloudAdvisor;

    @Resource
    private VectorStore pgVectorVectorStore;

    @Resource
    private QueryRewriter queryRewriter;


    @Resource
    private ToolCallback[] allTools;

    private final ChatClient chatClient;

    private final String SYSTEM_PROMPT = "扮演深耕恋爱心理领域的专家。开场向用户表明身份，告知用户可倾诉恋爱难题。\" +\n" +
            "            \"围绕单身、恋爱、已婚三种状态提问：单身状态询问社交圈拓展及追求心仪对象的困扰；\" +\n" +
            "            \"恋爱状态询问沟通、习惯差异引发的矛盾；已婚状态询问家庭责任与亲属关系处理的问题。\" +\n" +
            "            \"引导用户详述事情经过、对方反应及自身想法，以便给出专属解决方案。";

    public LoveApp(ChatModel dashscopeChatModel) {
        //初始化基于内存的对话记忆
        ChatMemory chatMemory = new InMemoryChatMemory();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory)
                        ,new MyLoggerAdvisor()
                )
                .build();

//        //初始化基于文件的对话记忆
//        String fileDir = System.getProperty("user.dir")+"/chat-memory";
//        ChatMemory chatMemory = new FileBasedChatMemory(fileDir);
//        chatClient = ChatClient.builder(dashscopeChatModel)
//                .defaultSystem(SYSTEM_PROMPT)
//                .defaultAdvisors(
//                        new MessageChatMemoryAdvisor(chatMemory)
//                )
//                .build();
    }

    record LoveReport(String title, List<String> suggestions){}

    public LoveReport doChatWithReport(String message, String chatId) {
        LoveReport loveReport = chatClient
                .prompt()
                .system(SYSTEM_PROMPT + "每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .entity(LoveReport.class);
        log.info("loveReport: {}", loveReport);
        return loveReport;
    }


    public String doChat(String message,String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec->spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY,chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .chatResponse();

        String content = response.getResult().getOutput().getText();
        log.info("content:{}",content);
        return content;
    }

    public String doChatWithRag(String message,String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec->spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY,chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .advisors(new MyLoggerAdvisor())
                //应用知识库问答
                .advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
                //基于云知识库
//                .advisors(loveAppRagCloudAdvisor)
                //基于检索增强PgVectorStore
//                .advisors(new QuestionAnswerAdvisor(pgVectorVectorStore))
                .call()
                .chatResponse();


        // 查询重写
//        String rewrittenMessage = queryRewriter.doQueryRewrite(message);
//        ChatResponse chatResponse = chatClient
//                .prompt()
//                .user(rewrittenMessage)
//                .call()
//                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content:{}",content);
        return content;
    }

    /**
     * 使用自定义工具
     * @param message
     * @param chatId
     * @return
     */
    public String doChatWithTools(String message,String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec->spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY,chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                //开启日志，以便观察
                .advisors(new MyLoggerAdvisor())
                .tools(allTools)
                .call()
                .chatResponse();

        String content = response.getResult().getOutput().getText();

        log.info("content:{}",content);

        return content;
    }


}
