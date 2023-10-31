create table employees2
(
    id         bigserial
        primary key,
    email_id   varchar(255),
    first_name varchar(255),
    last_name  varchar(255)
);

alter table employees2
    owner to postgres;

