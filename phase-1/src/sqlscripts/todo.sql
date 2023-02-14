drop table if exists to_dos;
create table to_dos (
   id SERIAL PRIMARY KEY,
   description varchar(255),
   user_id bigint,
   foreign key (user_id) references users (id)
);
