CREATE TABLE IF NOT EXISTS `dream_account` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `username` varchar(128) NOT NULL COMMENT '用户名',
  `amount` bigint NOT NULL COMMENT '账户金额',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_general_ci COMMENT='梦想账户';

CREATE TABLE IF NOT EXISTS `dream_account_log` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `content` varchar(128) NOT NULL COMMENT '内容'
  `create_time` timestamp NOT NULL DEFAULT current_timestamp COMMENT '创建时间'
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_general_ci COMMENT='梦想账户金额修改记录日志';

insert into dream_account(id,username,amount) values ('1','郝大','100');
insert into dream_account(id,username,amount) values ('2','锤子','50');