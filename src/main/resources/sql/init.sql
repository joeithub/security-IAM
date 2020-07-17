CREATE DATABASE IF NOT EXISTS `security` DEFAULT CHARACTER SET utf8;
USE `security`;
DROP TABLE IF EXISTS `tb_security_user`;
CREATE TABLE IF NOT EXISTS `tb_security_user`
(
    `id`          char(36)     NOT NULL COMMENT '标识id',
    `username`    varchar(32)  NOT NULL COMMENT '用户名',
    `password`    varchar(255) NOT NULL COMMENT '密码（MD5加密）',
    `name`        varchar(100) DEFAULT NULL COMMENT '姓名|昵称',
    `mobile`      varchar(20)  DEFAULT NULL COMMENT '手机',
    `email`       varchar(100) DEFAULT NULL COMMENT '邮箱',
    `user_state`  varchar(2)   default '0' comment '用户状态 0 启用 1 禁用',
    `auth_type`   varchar(2)   DEFAULT '00' COMMENT '认证类型00 local/10 ldap/11 radius/12 ad',
    `admin_flag`  int(1)       DEFAULT 0 COMMENT '标识 0-普通用户',
    `creator`     char(36)     NOT NULL COMMENT '创建人',
    `create_time` timestamp    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updator`     char(36)     NOT NULL COMMENT '修改人',
    `update_time` timestamp    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `state`       varchar(2)   DEFAULT '0' COMMENT '用户状态 0 正常|1 删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户基本信息表';

-- 角色表
-- 删除
DROP TABLE IF EXISTS `tb_security_role`;
-- 新建 用户组表
CREATE TABLE IF NOT EXISTS `tb_security_role`
(
    `id`          char(36)     NOT NULL COMMENT '标识id',
    `name`        varchar(100) NOT NULL COMMENT '角色名',
    `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
    `creator`     char(36)     NOT NULL COMMENT '创建人',
    `create_time` timestamp    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updator`     char(36)     NOT NULL COMMENT '修改人',
    `update_time` timestamp    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `state`       varchar(2)   DEFAULT '0' COMMENT '状态 0 正常|1 删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色表';

-- 用户与角色关系表
-- 删除
DROP TABLE IF EXISTS `tb_security_user_role_mp`;
-- 新建 用户与角色关系表
CREATE TABLE IF NOT EXISTS `tb_security_user_role_mp`
(
    `id`          char(36)  NOT NULL COMMENT '标识id',
    `role_id`     char(36)  NOT NULL COMMENT '角色标识',
    `user_id`     char(36)  NOT NULL COMMENT '用户标识',
    `creator`     varchar(36)        default null comment '创建人',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户与角色关系表';

-- 用于自动登录，记住我
CREATE TABLE IF NOT EXISTS `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- user

INSERT INTO `security`.`tb_security_user`(`id`, `username`, `password`, `name`, `mobile`, `email`, `user_state`, `auth_type`, `admin_flag`, `creator`, `create_time`, `updator`, `update_time`, `state`) VALUES ('0', 'security', '123456', 'security', '15151818258', 'joetonghao@126.com', '0', '00', 0, 'tong', '2020-03-03 15:52:53', '2020-03-03 15:52:53', '2020-03-03 15:52:53', '0');

INSERT INTO `security`.`tb_security_user`(`id`, `username`, `password`, `name`, `mobile`, `email`, `user_state`, `auth_type`, `admin_flag`, `creator`, `create_time`, `updator`, `update_time`, `state`) VALUES ('1', 'admin', '123456', 'admin', '15151818258', 'joetonghao@126.com', '0', '00', 0, 'tong', '2020-03-03 15:52:53', '2020-03-03 15:52:53', '2020-03-03 15:52:53', '0');

INSERT INTO `security`.`tb_security_user`(`id`, `username`, `password`, `name`, `mobile`, `email`, `user_state`, `auth_type`, `admin_flag`, `creator`, `create_time`, `updator`, `update_time`, `state`) VALUES ('2', 'tongq', '123456', 'tongq', '15151818258', 'joetonghao@126.com', '0', '00', 0, 'tong', '2020-03-03 15:52:53', 'c', '2020-03-03 15:52:53', '0');

-- role
INSERT INTO `security`.`tb_security_role`(`id`, `name`, `description`, `creator`, `create_time`, `updator`, `update_time`, `state`) VALUES ('0', 'super', '超级管理员', 'tong', '2020-03-03 15:52:53', 'tong', '2020-03-03 15:52:53', '0');

INSERT INTO `security`.`tb_security_role`(`id`, `name`, `description`, `creator`, `create_time`, `updator`, `update_time`, `state`) VALUES ('1', 'admin', '管理员', 'tong', '2020-03-03 15:52:53', 'tong', '2020-03-03 15:52:53', '0');

INSERT INTO `security`.`tb_security_role`(`id`, `name`, `description`, `creator`, `create_time`, `updator`, `update_time`, `state`) VALUES ('2', 'user', '用户', 'tong', '2020-03-03 15:52:53', 'tong', '2020-03-03 15:52:53', '0');

-- mp
INSERT INTO `security`.`tb_security_user_role_mp`(`id`, `role_id`, `user_id`, `creator`, `create_time`) VALUES ('0', '0', '0', 'tong', '2020-03-03 15:58:33');
INSERT INTO `security`.`tb_security_user_role_mp`(`id`, `role_id`, `user_id`, `creator`, `create_time`) VALUES ('1', '1', '1', 'tong', '2020-03-03 15:58:33');
INSERT INTO `security`.`tb_security_user_role_mp`(`id`, `role_id`, `user_id`, `creator`, `create_time`) VALUES ('2', '2', '2', 'tong', '2020-03-03 15:58:33');


-- oauth2 user
CREATE TABLE IF NOT EXISTS `tb_security_oauth2_user`(
	`id` VARCHAR(36) NOT NULL COMMENT '主键id',
	`user_id` VARCHAR(36) NOT NULL COMMENT '用户id',
	`oauthid` VARCHAR(36) NOT NULL COMMENT 'openid 钉钉id 等',
	`nickname` VARCHAR(255) COMMENT '昵称',
	`language` VARCHAR(255) COMMENT '语言',
	`sex` VARCHAR(10) COMMENT '性别',
	`province` VARCHAR(255) COMMENT '省份',
	`city` VARCHAR(255) COMMENT '城市',
	`country` VARCHAR(255) COMMENT '国家',
	`headimgurl` VARCHAR(255) COMMENT '头像',
	`privilege` VARCHAR(255) COMMENT '特权',
	`unionid` VARCHAR(36) COMMENT '统一id',
	PRIMARY key(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方用户表';