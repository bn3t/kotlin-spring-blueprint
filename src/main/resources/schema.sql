drop table if exists book cascade;

drop sequence if exists book_seq;

create sequence book_seq start with 1000 increment by 50;

create table book
(
    id    bigint not null,
    isbn  varchar(255),
    title varchar(255),
    primary key (id),
    unique (isbn)
);
