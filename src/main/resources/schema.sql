--create table APP_USERS(
--  ID int not null AUTO_INCREMENT,
--  NAME varchar(100) not null,
--  STATUS int,
--  PRIMARY KEY ( ID )
--);
create table app_user (
    id bigint not null AUTO_INCREMENT,
    name varchar(255),
    login varchar(255),
    password varchar(255),
    role smallint,
    status smallint,
    primary key (id));

create table folder (
    id bigint not null AUTO_INCREMENT,
    title varchar(255),
    owner_id bigint,
    primary key (id));

create table note (
    id bigint not null AUTO_INCREMENT,
    note_text varchar(255),
    title varchar(255),
    folder_id bigint,
    primary key (id));

