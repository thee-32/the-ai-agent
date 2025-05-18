//package com.the.theaiagent.advisor;
//
//import org.springframework.ai.chat.client.advisor.api.*;
//import reactor.core.publisher.Flux;
//
///**
// * 违禁词
// */
//public class SensitiveAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {
//    @Override
//    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
//        // 1. 处理请求（前置处理）
//        AdvisedRequest modifiedRequest = processRequest(advisedRequest);
//
//        // 2. 调用链中的下一个Advisor
//        AdvisedResponse response = chain.nextAroundCall(modifiedRequest);
//
//        // 3. 处理响应（后置处理）
//        return processResponse(response);
//
//    }
//
//    private AdvisedRequest processRequest(AdvisedRequest advisedRequest) {
//        String SENSITIVE = "Sensitive";
//        if(advisedRequest.userText().contains(SENSITIVE)){
//            return advisedRequest.("不能包括敏感词"+SENSITIVE);
//        }
//        return advisedRequest;
//    }
//
//    private AdvisedRequest processResponse(AdvisedResponse response) {
//        String SENSITIVE = "Sensitive";
//        if(response.adviseContext().contains(SENSITIVE)){
//            return response.adviseContext("不能包括敏感词"+SENSITIVE);
//        }
//        return response;
//    }
//
//    @Override
//    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
//        return null;
//    }
//
//    @Override
//    public String getName() {
//        return "";
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
