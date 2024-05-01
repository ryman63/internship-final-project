alter table if exists participants add column internship_id int8 not null
create table task_comment (id int8 generated by default as identity, author varchar(255), value varchar(255), write_date timestamp, task_id int8, primary key (id))
alter table if exists participants add constraint FKi00o56yb226c79m77623ao13j foreign key (internship_id) references internship
alter table if exists task_comment add constraint FK7kiv45on4bf6my8w8mhefy9jy foreign key (task_id) references tasks