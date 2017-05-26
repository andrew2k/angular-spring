SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;
SET search_path = public, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = false;

CREATE TABLE users (
	user_id SERIAL PRIMARY KEY
    username character varying(45) NOT NULL,
    password character varying(45) NOT NULL,
    profile character varying(45),
    enabled integer DEFAULT 1 NOT NULL
);
ALTER TABLE users OWNER TO postgres;
CREATE UNIQUE INDEX users_username_idx ON users (username);

CREATE TABLE registratino_waiting_list (
    username character varying(45) NOT NULL,
    password character varying(45) NOT NULL,
    code character varying(100),
    insert_date timestamp default current_timestamp
);
ALTER TABLE registratino_waiting_list OWNER TO postgres;
ALTER TABLE ONLY registratino_waiting_list ADD CONSTRAINT registratino_waiting_list_pkey PRIMARY KEY (code);

CREATE TABLE user_roles (
    user_role_id SERIAL PRIMARY KEY,
    username character varying(45) NOT NULL,
    role character varying(45) NOT NULL
);
ALTER TABLE user_roles OWNER TO postgres;
ALTER TABLE ONLY user_roles ADD CONSTRAINT user_roles_role_username_key UNIQUE (role, username);
--ALTER TABLE ONLY user_roles ADD CONSTRAINT fk__user_roles__username FOREIGN KEY (username) REFERENCES users(username);

CREATE TABLE profile_roles (
    profile_role_id SERIAL PRIMARY KEY,
    role character varying(45) NOT NULL,
    profile character varying(45) NOT NULL
);
ALTER TABLE profile_roles OWNER TO postgres;
ALTER TABLE ONLY profile_roles ADD CONSTRAINT profile_roles__role_profile_key UNIQUE (role, profile);
--ALTER TABLE ONLY profile_roles ADD CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users(username);








REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


COPY profile_roles(profile, role) FROM stdin;
USER_PROFILE	ROLE_USER2
\.

COPY users (username, password, profile, enabled) FROM stdin;
mkyong	123456	USER_PROFILE	1
alex	123456		1
\.

COPY user_roles (user_role_id, username, role) FROM stdin;
1	mkyong	ROLE_USER
2	mkyong	ROLE_ADMIN
3	alex	ROLE_USER
\.

delete from registratino_waiting_list;
delete from profile_roles
delete from user_roles;
delete from users;
insert into profile_roles values ('USER_PROFILE', 'role1');


drop table registratino_waiting_list;
drop table profile_roles
drop table user_roles;
drop table users;


