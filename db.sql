
use jwt_security;

create table Roles
(
    roleId   int auto_increment
        primary key,
    roleName varchar(40) null
);

create table Users
(
    userId   int auto_increment
        primary key,
    username varchar(100) null,
    password varchar(100) null
);

create table user_role
(
    id     int auto_increment
        primary key,
    userId int null,
    roleId int null,
    constraint userId
        unique (userId, roleId),
    constraint user_role_ibfk_1
        foreign key (userId) references Users (userId),
    constraint user_role_ibfk_2
        foreign key (roleId) references Roles (roleId)
);

create index roleId
    on user_role (roleId);

