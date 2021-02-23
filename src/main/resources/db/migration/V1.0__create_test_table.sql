create table post (
    id bigint(20),
    title varchar(20),
    category varchar(20),
    author_id char(36),
    primary key (id)
);

create table author (
    id char(36),
    name varchar(20),
    thumbnail varchar(20),
    primary key (id)
);

insert into post values
(1, '테스트', '테스트','1'),
(3, '테스트', '테스트','2'),
(2, '테스트', '테스트','2');

insert into author values
('1', 'Author1', '1'),
('2', 'Author2', '1');


/*
https://bezkoder.com/spring-boot-graphql-mysql-jpa/

https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/#schema
*/