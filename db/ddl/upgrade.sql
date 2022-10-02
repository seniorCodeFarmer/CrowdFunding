create table t_admin
(
    id int not null auto_increment, # 主键
    login_acct varchar(255) not null, # 登录账号
        user_pswd char(32) not null, # 登录密码
        user_name varchar(255) not null, # 昵称
        email varchar(255) not null, # 邮件地址
        create_time char(19), # 创建时间
        primary key (id)
);

ALTER TABLE `project_crowd`.`t_admin`
    ADD UNIQUE INDEX(`login_acct`);

CREATE TABLE `t_role`
(
    `id`   int(11) NOT NULL,
    `name` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `inner_admin_role`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `admin_id` int(11) DEFAULT NULL,
    `role_id`  int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_auth`
(
    `id`          INT ( 11 ) NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(200) DEFAULT NULL,
    `title`       VARCHAR(200) DEFAULT NULL,
    `category_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `inner_role_auth`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `role_id` int(11) DEFAULT NULL,
    `auth_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;