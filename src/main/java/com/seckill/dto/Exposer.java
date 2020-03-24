package com.seckill.dto;

/***
 * @description: 暴露秒杀地址
 * @author pikav
 * @date 2020-3-14
 */
public class Exposer {

    private boolean exposer;

    // 加密
    private String md5;

    private long seckillId;

    // 系统当前时间
    private long now;

    // 秒杀开启时间
    private long start;

    // 秒杀结束时间
    private long end;

    // 秒杀开启
    public Exposer(boolean exposer, String md5, long seckillId) {
        this.exposer = exposer;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    // 秒杀关闭
    public Exposer(boolean exposer, long seckillId, long now, long start, long end) {
        this.exposer = exposer;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposer, long seckillId) {
        this.exposer = exposer;
        this.seckillId = seckillId;
    }

    public boolean isExposer() {
        return exposer;
    }

    public void setExposer(boolean exposer) {
        this.exposer = exposer;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposer=" + exposer +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

}
