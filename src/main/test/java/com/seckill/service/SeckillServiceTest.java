package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-mapper.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("list={}",seckillList);
    }

    @Test
    public void getByID() {
        long id = 1008;
        Seckill seckill = seckillService.getByID(id);
        logger.info("sckill{}",seckill);
    }

    @Test
    public void exportSeckillUrl() {
        long id = 1008;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer{}",exposer);
        if(exposer.isExposer()) {
            long phone = 13187200842L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id,phone,md5);
                logger.info("resutl={}",execution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("秒杀未开启,exposer={}", exposer);
        }
    }

}