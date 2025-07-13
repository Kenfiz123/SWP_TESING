package com.group2.ADN;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // dùng cấu hình test để bỏ qua security
public class AdnApplicationTests {

    @Test
    void contextLoads() {
    }
}
