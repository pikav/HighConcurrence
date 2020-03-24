package com.seckill.service;


import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

import java.util.List;

/***
 * @description:
 * @author pikav
 * @date 2020-3-14
 */
public interface SeckillService {


    /***
     * @description: 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /***
     * @description: 查询单个秒杀记录
     * @param: seckiilId
     * @return
     */
    Seckill getByID(long seckillId);

    /***
     * @description: 秒杀开启,输出秒杀接口；秒杀关闭，输出系统时间和秒杀时间
     * @param: seckiilId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * @description 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;

    /**
     * @description 通过存储过程执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);


}
