高并发优化方案总结：
1. 把静态资源部署在CDN, 用户刷新静态页面访问静态资源不再访问服务器, 减小服务器压力
2. redis缓存提高查询速度
3. 用存储过程执行插入修改语句
