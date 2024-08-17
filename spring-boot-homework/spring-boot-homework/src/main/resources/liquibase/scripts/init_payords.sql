-- changeset vvolski:1
create table payOrds
(
    id             integer not null constraint payOrds_pk primary key,
    employee_Id    integer not null,
    date           date    not null,
    sum            real    not null,
    salary_Date    date    not null,
    FOREIGN KEY (employee_Id) REFERENCES employees (id)
);
alter table payOrds
    owner to postgres;

create sequence payOrds_sequence as integer;
alter sequence payOrds_sequence owner to postgres;