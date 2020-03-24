package com.seckill.exception;

/***
 * @description: 封装重复秒杀操作异常
 * @author pikav
 * @date 2020-3-14
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }

}
