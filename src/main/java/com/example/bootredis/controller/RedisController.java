package com.example.bootredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class RedisController {

    @Autowired
    RedisTemplate redisTemplate;

    @ResponseBody
    @RequestMapping("/redisTest")
    public String hjkl() {
        System.out.println("change version v1.02");
        System.out.println("正常合并分支");
        System.out.println("hot fix test");
        redisTemplate.opsForValue().set("name", "lbh");
        String name = (String) redisTemplate.opsForValue().get("name");
        return name;
    }

    @RequestMapping("/testLock")
    public void lock() {
        String string = UUID.randomUUID().toString();
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent("lock", string, 10, TimeUnit.SECONDS);
        if (ifAbsent) {
            //获取到了锁,执行+1操作
            redisTemplate.opsForValue().increment("num");
            String lock = (String) redisTemplate.opsForValue().get("lock");
            if (lock.equals(string)) {
                //释放自己的锁
                redisTemplate.delete("lock");
            }
        } else {
            //线程休眠0.5秒后在访问
            try {
                Thread.sleep(500);
                lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
