-- Run the following commands to delete all table and re-initialise database--
 drop table user_profile CASCADE;
 drop table wall_post CASCADE;
 drop table user_like CASCADE;
 drop table friend_list CASCADE;
 drop table logs CASCADE;

 -- user profile related sql --
 create table user_profile (
 	username varchar(60) not null,
 	password varchar(20) not null,
 	first_name varchar(60) not null,
 	last_name varchar(60) not null,
 	email varchar(200) not null,
 	gender varchar(20) not null,
 	date_of_birth date not null,
 	session_id int,
 	primary key(username),
 	constraint chk_gender check
 	(gender = 'male' or gender = 'female')
 	-- session_id null == user is not online --
 );
-- end of profile sql--

--wall post related sql --
create table wall_post(
	username varchar(60) not null,
	id serial,
	content text not null,
	img text,
	post_date timestamp not null,
	foreign key (username) references user_profile(username),
	primary key (id)
);
-- end of wall post sql --

-- user_like related sql --
create table user_like(
	username varchar(60) not null,
	wall_id int not null,
	time_liked TIMESTAMP not null,
	foreign key (username) references user_profile(username),
	foreign key (wall_id) references wall_post(id),
	primary key (username, wall_id)
);
-- end of user_like sql --

-- friend list related sql --
create table friend_list(
	username_primary varchar(60) not null,
	username_secondary varchar(60) not null,
	accepted boolean,
	foreign key (username_primary) references user_profile(username),
	foreign key (username_secondary) references user_profile(username),
	primary key (username_primary, username_secondary)--,
	--constraint chk_boolean_accepted check
	--(accepted = 0 or accepted = 1)
	-- if rejected delete the entry from database, accepted == false means the other party not accept yet --
);

-- user logs
create table logs(
  log_id serial,
  username varchar(60),
  user_action varchar(1000) not null,
  time timestamp not null,
  PRIMARY KEY (log_id),
  FOREIGN KEY (username) REFERENCES  user_profile(username)
);