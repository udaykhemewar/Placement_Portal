create table public.attendance (
                                   id bigint primary key not null default nextval('attendance_id_seq'::regclass),
                                   attendance boolean,
                                   date timestamp(6) without time zone,
                                   employee_id bigint,
                                   foreign key (employee_id) references public.employees2 (id)
                                       match simple on update no action on delete no action
);