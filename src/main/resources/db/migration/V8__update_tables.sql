create table _user
(
    id                   integer not null
        primary key,
    email                varchar(255),
    first_name           varchar(255),
    last_name            varchar(255),
    password             varchar(255),
    reset_password_token varchar(255),
    role                 varchar(255)
);

alter table _user
    owner to postgres;

create table _weather
(
    id                      bigserial
        primary key,
    avg_temperature_by_city double precision,
    city                    varchar(255),
    country                 varchar(255),
    description             varchar(255),
    temperature             double precision not null,
    timestamp               date,
    wind_speed              double precision not null
);

alter table _weather
    owner to postgres;

