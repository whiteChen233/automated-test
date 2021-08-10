-- 创建序列
create sequence business_sequence increment by 1 minvalue 1 no maxvalue start with 1;
create sequence log_sequence increment by 1 minvalue 1 no maxvalue start with 1;

-- 创建 user 表
create table sys_user
(
  id          int         not null primary key,
  username    varchar(32) not null unique,
  password    varchar(128),
  status      int        default 0,
  is_admin    varchar(1) default 0,
  nickname    varchar(64),
  gender      int        default 0,
  create_time timestamp  default current_time,
  create_by   varchar(32),
  create_time timestamp,
  create_by   varchar(32)
);
