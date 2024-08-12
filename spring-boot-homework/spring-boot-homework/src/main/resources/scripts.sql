create table employees
(
    id             integer not null constraint employees_pk primary key,
    firstName      text    not null,
    lastName       text    not null,
    departmentName text    not null
);
alter table employees owner to postgres;

create sequence employees_sequence as integer;
alter sequence employees_sequence owner to postgres;

create table payOrds
(
    id            integer not null constraint payOrds_pk primary key,
    employeeId    integer not null,
    date          date    not null,
    sum           real    not null,
    salaryDate    date    not null,
    FOREIGN KEY (employeeId) REFERENCES employees (id)
);
alter table payOrds
    owner to postgres;

create sequence payOrds_sequence as integer;
alter sequence payOrds_sequence owner to postgres;