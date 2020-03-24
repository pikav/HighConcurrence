package com.seckill.mapper;

import com.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * @description:
 * @author pikav
 * @date 2020-3-14
 */
public interface SeckillMapper {

    // 减库存
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    // 根据id查询秒杀商品
    Seckill queryById(long seckillId);

    // 根据偏移量查询秒杀商品列表
    List<Seckill> queryAll(@Param("offet") int offet, @Param("limit") int limit);

    // 使用存储过程调用
    void killByProcedure(Map<String,Object> paramMap);

}
