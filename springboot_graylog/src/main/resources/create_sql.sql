CREATE DATABASE IF NOT EXISTS graylog_db default character SET utf8mb4 collate utf8mb4_general_ci;

CREATE TABLE `graylog_log` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `content` varchar(1000) DEFAULT NULL COMMENT '内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COMMENT='记录日志内容';