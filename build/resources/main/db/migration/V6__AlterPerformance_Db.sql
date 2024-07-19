alter table if exists performance drop column grade;
alter table if exists performance add column comment varchar(255);
alter table if exists performance add column write_date timestamp;
