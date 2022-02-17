package com.example.bootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoKill {

    @Autowired
    RedisTemplate redisTemplate;


    public void kill(String prodid, String userid) {
        redisTemplate.setEnableTransactionSupport(true);
        if (prodid == null || userid == null) {
            System.out.println("prodid或userid为空");
            return;
        }

        String userKey = "user:" + prodid;
        String prodKey = "pro:" + prodid;

        if (redisTemplate.opsForValue().get(prodKey)==null) {
            System.out.println("秒杀尚未开始");
            return;
        }

        if (redisTemplate.opsForSet().isMember(userKey, userid)) {
            System.out.println("该用户已经参与过秒杀了，无法重复参与");
            return;
        }
        int i = (int)redisTemplate.opsForValue().get(prodKey);
        if (i <= 0) {
            System.out.println("秒杀已经结束了");
        } else {

            SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
                @Override
                public Object execute(RedisOperations operations) throws DataAccessException {
                    operations.watch(prodKey);
                    operations.multi();
                    operations.opsForValue().decrement(prodKey);
                    operations.opsForSet().add(userKey, userid);
                    return operations.exec();
                }
            };
            redisTemplate.execute(sessionCallback);
//            redisTemplate.watch(prodKey);
//            redisTemplate.multi();
//            redisTemplate.opsForValue().decrement(prodKey);
//            redisTemplate.opsForSet().add(userKey, userid);
//            List exec = redisTemplate.exec();
//            if (exec == null || exec.size() == 0) {
//                System.out.println("秒杀成功");
//                System.out.println(exec);
//            } else {
//                System.out.println("秒杀成功");
//                System.out.println(exec);
//            }
        }
    }
}
