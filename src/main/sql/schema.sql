--创建数据库脚本
CREATE  DATABASE seckill;
--使用数据库
use seckill;
--创建秒杀库存表
CREATE TABLE `seckill`  (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '秒杀开启时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`) USING BTREE,
  INDEX `idx_start_time`(`start_time`) USING BTREE,
  INDEX `idx_end_time`(`end_time`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1012 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '秒杀库存表' ROW_FORMAT = Compact;


--初始化数据
INSERT INTO `seckill` VALUES (1008, '1元秒杀神舟战神笔记本', 97, '2020-03-23 13:07:30', '2020-04-01 00:00:00', '2020-03-15 00:00:00');
INSERT INTO `seckill` VALUES (1009, '1元秒杀三星S20', 100, '2020-03-23 13:02:21', '2020-04-01 00:00:00', '2020-03-15 00:00:00');
INSERT INTO `seckill` VALUES (1010, '1元秒杀方特一日游', 100, '2020-03-23 13:02:24', '2020-04-01 00:00:00', '2020-03-15 00:00:00');
INSERT INTO `seckill` VALUES (1011, '1元秒杀湖人场边球票', 100, '2020-03-23 13:02:31', '2020-04-01 00:00:00', '2020-03-15 00:00:00');

--秒杀成功明细表
--用户登录认证的相关的信息
CREATE TABLE `success_killed`  (
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品id',
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机号',
  `state` bigint(20) NOT NULL DEFAULT -1 COMMENT '状态表示：-1：无效，0：成功，1：已付款',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`, `user_phone`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '秒杀成功明细表' ROW_FORMAT = Compact;
