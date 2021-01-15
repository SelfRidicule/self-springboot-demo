package self.integration;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import self.Application;

import javax.inject.Inject;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class MyIntegrationTest {

    @Inject
    Environment environment;

    @Test
    public void indexHtmlIsAccessible() {
        String port = environment.getProperty("local.server.port");
        System.out.println(port);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:" + port + "/auth");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            //获取response的响应状态
            System.out.println(response.getStatusLine());
            //获取响应内容
            HttpEntity entity = response.getEntity();
            String responseContentJson = EntityUtils.toString(entity, "utf8");
            //确保被消耗
            EntityUtils.consume(entity);
            //断言
            Assertions.assertEquals(200, response.getStatusLine().getStatusCode());
            Assertions.assertTrue(responseContentJson.contains("用户没有登录"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
