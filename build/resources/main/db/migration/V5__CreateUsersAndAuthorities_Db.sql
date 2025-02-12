create table users(
                      id bigint generated by default as identity primary key,
                      username varchar(50) not null unique,
                      password varchar(500) not null,
                      email varchar(255) not null,
                      enabled boolean not null);

create table authorities (
                             id bigint generated by default as identity,
                             username varchar(50) not null,
                             authority varchar(50) not null,
                             constraint fk_authorities_users foreign key(id) references users(id));

create unique index ix_auth_username on authorities (username, authority);

INSERT INTO users (username, password, email, enabled)
VALUES ('admin01', '{noop}admin', 'admin@example.com', true);

INSERT INTO users (username, password, email, enabled)
VALUES ('user01', '{noop}user', 'user@example.com', true);


INSERT INTO authorities (username, authority)
VALUES ('admin01', 'ROLE_ADMIN');

INSERT INTO authorities (username, authority)
VALUES ('user01', 'ROLE_USER');

commit;