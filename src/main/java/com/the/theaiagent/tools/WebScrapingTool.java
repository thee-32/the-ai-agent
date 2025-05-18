package com.the.theaiagent.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
     * 自定义网页抓取工具
     */
    public  class WebScrapingTool {
        /**
         * wen page scrape
         * @param url
         * @return
         */
        @Tool(description = "Scrape the content of web page")
        public String scrapeWebPage(@ToolParam(description = "URL of the web page to scrape") String url) {
            try{
                Document doc = Jsoup.connect(url).get();
                return doc.html();
            }catch (Exception e){
                return "Error web scrape: " + e.getMessage();
            }
        }
    }