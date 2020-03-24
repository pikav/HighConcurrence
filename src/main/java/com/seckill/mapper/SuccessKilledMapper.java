package com.seckill.mapper;

import com.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/***
 * @description:
 * @author pikav
 * @date 2020-3-14
 */
public interface SuccessKilledMapper {

    // 插入购买商品明细
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    // 根据id(联合ID:seckillId + phone )查询SuccessKilled并携带秒杀产品对象实体
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
