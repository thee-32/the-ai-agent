package com.the.theaiagent.agent;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ReAct(resasoning and Acting) 模式的代理类
 * 实现了思考-行动的循环模式
 */
@Data
@EqualsAndHashCode(callSuper=true)
public abstract class ReActAgent  extends  BaseAgent{
    /**
     * 处理当前状态决定下一步行动
     * @return 是否需要执行行动，true 表示需要执行，false表示不需要执行
     */
    public abstract boolean think();

    /**
     * 执行决定的行动
     * @return 行动执行结果
     */
    public abstract String act();

    @Override
    public String step(){
        try{
            boolean shouldAct = think();
            if(!shouldAct){
                return "思考完成 - 无需行动";
            }
            return act();
        } catch (Exception e) {
            //纪录异常日志
            e.printStackTrace();
            return "步骤执行失败：" + e.getMessage();
        }
    }

}
