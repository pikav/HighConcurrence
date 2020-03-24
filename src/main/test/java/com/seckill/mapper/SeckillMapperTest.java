package com.seckill.mapper;

import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mapper.xml"})
public class SeckillMapperTest {

    @Resource
    private SeckillMapper seckillMapper;

    @Test
    public void reduceNumber() {
        Date killTime = new Date();
        int updateCount = seckillMapper.reduceNumber(1008L,killTime);
        System.out.println("updateCount=" + updateCount);
    }

    @Test
    public void queryById() {
        long id = 1008;
        Seckill seckill = seckillMapper.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() {
        List<Seckill> seckillList = seckillMapper.queryAll(0,20);
        for (Seckill seckill: seckillList) {
            System.out.println(seckill);
        }
    }
}