create database notification;
USE database notification;

drop table if exists eventlog;
create table eventlog
(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    "action" VARCHAR(255)
);
