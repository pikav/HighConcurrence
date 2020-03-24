package com.seckill.service.impl;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStatEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.mapper.SeckillMapper;
import com.seckill.mapper.SuccessKilledMapper;
import com.seckill.mapper.cache.RedisDao;
import com.seckill.service.SeckillService;
import com.seckill.util.ConstantsUtil;
import com.seckill.util.ValidateUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private SuccessKilledMapper successKilledMapper;

    @Autowired
    private RedisDao redisDao;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillMapper.queryAll(0,4);
    }

    @Override
    public Seckill getByID(long seckillId) {
        return seckillMapper.queryById(seckillId);
    }

    @Override
    @Transactional
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(ValidateUtil.isNullOrEmpty(seckill)) {
            seckill = seckillMapper.queryById(seckillId);
            if (ValidateUtil.isNullOrEmpty(seckill)) {
                return new Exposer(false,seckillId);
            } else {
                redisDao.putSeckill(seckill);
            }
        }

        if(ValidateUtil.isNullOrEmpty(seckill)) {
            return new Exposer(false, seckillId);
        }
        Date nowTime = new Date();
        if(!ValidateUtil.isInThatTimeFrame(nowTime, seckill.getStartTime(),seckill.getEndTime())) {
            return new Exposer(false,
                    seckillId,
                    nowTime.getTime(),
                    seckill.getStartTime().getTime(),
                    seckill.getEndTime().getTime());
        }
        // TODO 加密, 转化字符为编码，不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        if(ValidateUtil.isNullOrEmpty(md5) || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("秒杀系统遭受非法攻击");
        }
        Date nowTime = new Date();
        try {
            // 记录购买
            int insertCount = successKilledMapper.insertSuccessKilled(seckillId,userPhone);
            if(insertCount <= 0) {
                throw new RepeatKillException("您也成功秒杀本商品");
            } else {
                // 减库存
                int updateCount = seckillMapper.reduceNumber(seckillId, nowTime);
                if(updateCount <= 0) {
                    // 未更新记录
                    throw new SeckillCloseException("秒杀活动已经结束了");
                } else {
                    // 秒杀成功
                    SuccessKilled successKilled = successKilledMapper.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (RepeatKillException e1) {
            throw e1;
        } catch (SeckillCloseException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 编译期异常 转换为运行期异常(spring事务可以做回滚)
            throw  new SeckillException("异常信息:" + e.getMessage());
        }
    }

    @Override
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
        if (md5 == null ||!md5.equals(getMD5(seckillId))){
            return new SeckillExecution(seckillId,SeckillStatEnum.DATA_REWRITE);
        }
        Date killTime = new Date();
        Map<String,Object> map = new HashMap<>();
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("killTime",killTime);
        map.put("result",null);
        //执行存储过程，result被赋值
        try {
            seckillMapper.killByProcedure(map);
            int result = MapUtils.getInteger(map,"result",-2);
            System.out.println(result);
            if (result == 1){
                SuccessKilled successKilled = successKilledMapper
                        .queryByIdWithSeckill(seckillId,userPhone);
                return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,successKilled);
            }else {
                return new SeckillExecution(seckillId,SeckillStatEnum.stateOf(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SeckillExecution(seckillId,SeckillStatEnum.INSER_ERROR);
        }
    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" + ConstantsUtil.SLAT;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
