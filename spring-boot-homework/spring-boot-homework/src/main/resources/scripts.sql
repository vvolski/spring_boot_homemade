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