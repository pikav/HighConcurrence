package com.seckill.exception;

/***
 * @description: 所有秒杀异常
 * @author pikav
 * @date 2020-3-14
 */

public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }

}
