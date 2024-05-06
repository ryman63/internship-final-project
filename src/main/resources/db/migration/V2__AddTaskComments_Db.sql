alter table if exists participant add column internship_id int8 not null;
alter table if exists participant add constraint FK_participantInternship foreign key (internship_id) references internship;