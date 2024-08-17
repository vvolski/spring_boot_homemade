-- changeset vvolski:1
create table employees
(
    id              integer not null constraint employees_pk primary key,
    first_Name      text    not null,
    last_Name       text    not null,
    department_Name text    not null
);
alter table employees owner to postgres;

create sequence employees_sequence as integer;
alter sequence employees_sequence owner to postgres;