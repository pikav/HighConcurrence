package com.seckill.mapper;

import com.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mapper.xml"})
public class SuccessKilledMapperTest {

    @Resource
    private SuccessKilledMapper successKilledMapper;

    @Test
    public void insertSuccessKilled() {
        long id = 1008L;
        long phone = 13187200043L;
        int inserCount = successKilledMapper.insertSuccessKilled(id,phone);
        System.out.println(inserCount);
    }

    @Test
    public void queryByIdWithSeckill() {
        long id = 1008L;
        long phone = 13187200043L;
        SuccessKilled successKilled = successKilledMapper.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}