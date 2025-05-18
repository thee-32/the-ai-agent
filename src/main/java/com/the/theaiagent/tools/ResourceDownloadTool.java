package com.the.theaiagent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.the.theaiagent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.File;

/**
 * 自定义资源下载器
 */
public class ResourceDownloadTool {

    @Tool(description = "Download a resource from a given url")
    public String downloadResource(@ToolParam(description = "URL of the resource to download") String url
            , @ToolParam(description = "Name of the file to save the downloaded resource") String fileName) {
        String fileDir = FileConstant.FILE_SAVE_DIR+"/download";
        String filePath = fileDir + "/" + fileName;
        try{
            //创建目录
            FileUtil.mkdir(fileDir);
            //使用hutool的downloadFile方法下载资源
            HttpUtil.downloadFile(url, new File(filePath));
            return "Resource downloaded successfully to: "+filePath;
        }catch (Exception e){
            return "Error downloading resource: "+e.getMessage();
        }

    }
}
