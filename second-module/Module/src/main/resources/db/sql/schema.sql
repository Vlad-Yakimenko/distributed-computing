CREATE TABLE citizen_type
(
    type_name varchar(50) primary key,
    language  varchar(50) not null
);

CREATE TABLE region
(
    region_name  varchar(50) primary key,
    area         int4        not null,
    citizen_type varchar(50) not null REFERENCES citizen_type (type_name)
);

CREATE TABLE weather_record
(
    id            serial primary key,
    record_date   date        not null,
    temperature   int4        not null,
    precipitation varchar(50) not null,
    region        varchar(50) not null REFERENCES region (region_name)
);