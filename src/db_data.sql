
-- user profile data --

insert into user_profile(username, password,first_name, last_name, email, gender, date_of_birth, session_id) values ('z5039157', 'comp9321', 'Andre', 'Ang', 'z5039157@student.unsw.edu.au', 'male', '25/12/1996', null);

insert into user_profile(username, password, email, first_name, last_name, gender, date_of_birth, session_id) values ('z5000000', 'helloworld', 'James', 'Moriarty' 'z5000000@hotmail.com', 'male', '31/12/1996', null);

insert into user_profile(username, password, email, first_name, last_name, gender, date_of_birth, session_id) values ('z0000000', 'hiworld', 'Agatha', 'Christie', 'z0000000@hotmail.com', 'female', '14/02/1996', null);

insert into user_profile(username, password, email, first_name, last_name, gender, date_of_birth, session_id) values ('z0000001', 'world', 'Irene', 'Adler' 'z0000001@hotmail.com', 'female', '31/10/1996', null);

-- friend list --
insert into friend_list(username_primary, username_secondary, accepted) values ('z0000001', 'z0000000', 1);