drop table if exists public.user_reviews;
drop table if exists public.users;
drop sequence if exists public.user_id_sequence;
drop sequence if exists public.user_review_id_sequence;
drop schema if exists public;
create schema public;
grant all on schema public to "user-service";
grant all on schema public to public;

create sequence public.user_id_sequence
    start with 100;

alter sequence public.user_id_sequence owner to "user-service";

create sequence public.user_review_id_sequence
    start with 100;

alter sequence public.user_review_id_sequence owner to "user-service";


create table public.user_reviews
(
    create_date   timestamp(6),
    id            bigint       not null
        primary key default nextval('user_review_id_sequence'),
    update_date   timestamp(6),
    user_id       bigint       not null,
    comment       varchar(255),
    restaurant_id varchar(255) not null,
    score         varchar(255) not null
        constraint user_reviews_score_check
            check ((score)::text = ANY
                   ((ARRAY ['ONE'::character varying, 'TWO'::character varying, 'THREE'::character varying, 'FOUR'::character varying, 'FIVE'::character varying])::text[])),
    unique (user_id, restaurant_id)
);

alter table public.user_reviews
    owner to "user-service";

create index idx1u771nlqfej1mro2fo8bhrr57
    on public.user_reviews (user_id);

create index idxamff53fxmjptwwad19kjtyckw
    on public.user_reviews (restaurant_id);

create table public.users
(
    birth_date   date,
    latitude     double precision not null
        constraint users_latitude_check
            check ((latitude >= ('-90'::integer)::double precision) AND (latitude <= (90)::double precision)),
    longitude    double precision not null
        constraint users_longitude_check
            check ((longitude >= ('-180'::integer)::double precision) AND (longitude <= (180)::double precision)),
    create_date  timestamp(6),
    id           bigint           not null
        primary key default nextval('user_id_sequence'),
    update_date  timestamp(6),
    phone_number varchar(15)
        unique,
    status       varchar(15)      not null
        constraint users_status_check
            check ((status)::text = ANY ((ARRAY ['ACTIVE'::character varying, 'PASSIVE'::character varying])::text[])),
    username     varchar(15)      not null
        unique,
    email        varchar(63)      not null
        unique,
    name         varchar(63)      not null,
    surname      varchar(63)      not null
);

alter table public.users
    owner to "user-service";

