package com.the.theaiagent.agent;

import com.the.theaiagent.agent.model.AgentState;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;


import java.util.ArrayList;
import java.util.List;

/**
 * 抽象代理基础代理类，用于管理代理状态和执行能力
 */
@Data
@Slf4j
public  abstract class BaseAgent {

    //核心特性
    private String name;


    //提示
    private String systemPrompt;
    private String nextStepPrompt;

    //状态
    private AgentState state = AgentState.IDLE;

    //执行控制
    private int maxSteps = 10;
    private int currentStep  = 0;

    //LLM
    private ChatClient chatClient;

    //Memory(维持上下文）
    private List<Message> messageList = new ArrayList<>();

    /**
     *  运行代理
     * @param userPrompt
     * @return
     */
    public String run(String userPrompt){
        if(this.state == AgentState.IDLE){
            throw new RuntimeException("Cannot run agent because the state is "+this.state);
        }
        if(StringUtils.isBlank(userPrompt)){
            throw new RuntimeException("Cannot run agent because the user prompt is empty");
        }

        //更改状态
        state = AgentState.RUNNING;

        //记录消息上下文
        messageList.add(new UserMessage(userPrompt));
        //保存结果列表
        List<String> results = new ArrayList<>();
        try{
           for(int i=0; i<maxSteps && state != AgentState.FINISH; i++){
               int stepNumber = i+1;
               currentStep = stepNumber;
               log.info("Executing Step "+stepNumber+"/"+maxSteps);
               //单步执行
               String stepResult = step();
               String result = "Step "+stepNumber+": "+stepResult;
               results.add(result);
           }

           //检查是否超出步骤限制
            if(currentStep>=maxSteps){
                state = AgentState.FINISH;
                results.add("Terminated: Reached max steps ("+maxSteps+")");
            }
            return String.join("\n", results);
        }catch (Exception e){
            state = AgentState.ERROR;
            log.error(e.getMessage(),e);
            return"Executing error: "+e.getMessage();
        }finally {
            //清理资源
            this.cleanup();
        }
    }

    /**
     * 执行单个步骤
     * @return
     */
    public abstract String step();

    protected void cleanup(){
        //子类重写此方法来清理资源
    }
}
