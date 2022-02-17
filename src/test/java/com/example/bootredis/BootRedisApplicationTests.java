package com.example.bootredis;

import com.example.bootredis.controller.SecondKill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootRedisApplicationTests {
    @Autowired
    SecondKill secondKill;

    @Test
    void contextLoads() {
        secondKill.ghjk();

    }

}
