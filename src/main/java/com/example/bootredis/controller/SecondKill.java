package com.example.bootredis.controller;

import com.example.bootredis.service.DoKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecondKill {

    @Autowired
    DoKill doKill;

    @RequestMapping("/secondKill")
    public void ghjk() {
        doKill.kill("1010","279268");
    }
}
