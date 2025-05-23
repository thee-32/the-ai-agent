package com.the.theaiagent.agent;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TheManusTest {

    @Resource
    private TheManus theManus;

    @Test
    public void test() {
        String userPrompt = """
                我的另一半居住在南山区，请帮我找到五公里内适合的约会地点，
                并结合一些网络图片，制定一份详细的约会计划，
                并以PDF格式输出
                """;
        String answer = theManus.run(userPrompt);
        Assertions.assertNotNull(answer);
    }
}
