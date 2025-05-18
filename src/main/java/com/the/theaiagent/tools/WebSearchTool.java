package com.the.theaiagent.tools;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自定义联网搜索
 */
public class WebSearchTool {

    private final static String WEB_SEARCH_URL = "https://www.searchapi.io/api/v1/search";

    private final String apiKey;
    public WebSearchTool(String apiKey) {
        this.apiKey = apiKey;
    }


    @Tool(description = "Search information by Baidu Search Engine")
    public String searchWeb(@ToolParam(description = "Search  query keyword") String query) {

        Map<String, Object> params = new HashMap<>();
        params.put("q", query);
        params.put("apiKey", apiKey);
        params.put("engine", "Baidu");

        try{
            String response = HttpUtil.get(WEB_SEARCH_URL,params);
            //去出返回结果的前5条
            JSONObject jsonObject = JSONUtil.parseObj(response);
            //提取 organic_results 部分
            JSONArray organicResults = jsonObject.getJSONArray("organic_results");
            List<Object> objects = organicResults.subList(0,5);
            //拼接搜索结果为字符串
            String result = objects.stream().map(obj->{
                JSONObject tmpJSONObject = (JSONObject)obj;
                return tmpJSONObject.toString();
            }).collect(Collectors.joining(","));

            return result;
        }catch (Exception e){
            return "Error searching Baidu:" + e.getMessage();
        }
    }

}
