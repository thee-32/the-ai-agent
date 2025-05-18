package com.the.theaiagent.tools;

import cn.hutool.core.io.FileUtil;
import com.the.theaiagent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;


/**
 * 自定义文件操作工具类
 */
public class FileOperationTool {

    private  final static String FILE_DIR = FileConstant.FILE_SAVE_DIR + "/file";

    /**
     * 读文件
     * @param fileName
     * @return
     */
    @Tool(description = "read content from a file")
    public String readFile(@ToolParam(description = "name  of the file to read") String fileName){
        String filePath = FILE_DIR +"/" + fileName;

        try{
            return FileUtil.readUtf8String(filePath);
        }catch (Exception e){
            return "Error read file:" +e.getMessage();
        }
    }


    @Tool(description = " write content to a file")
    public String writeFile(@ToolParam(description = "name  of the file to write") String fileName,
                            @ToolParam(description = "content of the file to write") String content){
        String filePath = FILE_DIR +"/" + fileName;

        try{
            //创建目录
            FileUtil.mkdir(FILE_DIR);
            FileUtil.writeUtf8String(content,filePath);

            return "Success write file:" +fileName;
        }catch (Exception e){
            return "Error write file:" +e.getMessage();
        }
    }
}
