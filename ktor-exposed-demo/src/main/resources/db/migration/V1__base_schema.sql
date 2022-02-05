create sequence patient_registration_id_seq
increment 1
start 10
minvalue 10;

create table patient (
id integer not null default nextval('patient_registration_id_seq'),
first_name varchar not null,
last_name varchar not null
);
