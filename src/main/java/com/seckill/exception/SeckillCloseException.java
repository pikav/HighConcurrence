package com.seckill.exception;

/***
 * @description: 秒杀关闭操作异常
 * @author pikav
 * @date 2020-3-14
 */

public class SeckillCloseException extends SeckillException{

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }

}
