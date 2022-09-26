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