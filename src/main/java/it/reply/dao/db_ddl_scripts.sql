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
    username character varying(45) NOT NULL,
    password character varying(45) NOT NULL,
    profile character varying(45),
    enabled integer DEFAULT 1 NOT NULL
);
ALTER TABLE users OWNER TO postgres;
ALTER TABLE ONLY users ADD CONSTRAINT users_pkey PRIMARY KEY (username);



CREATE TABLE registratino_waiting_list (
    username character varying(45) NOT NULL,
    password character varying(45) NOT NULL,
    code character varying(100),
    insert_date timestamp default current_timestamp
);
ALTER TABLE registratino_waiting_list OWNER TO postgres;
ALTER TABLE ONLY registratino_waiting_list ADD CONSTRAINT registratino_waiting_list_pkey PRIMARY KEY (code);




CREATE TABLE user_roles (
    user_role_id integer NOT NULL,
    username character varying(45) NOT NULL,
    profile character varying(45) NOT NULL
);
ALTER TABLE user_roles OWNER TO postgres;

CREATE SEQUENCE user_roles_user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE user_roles_user_role_id_seq OWNER TO postgres;
ALTER SEQUENCE user_roles_user_role_id_seq OWNED BY user_roles.user_role_id;
ALTER TABLE ONLY user_roles ALTER COLUMN user_role_id SET DEFAULT nextval('user_roles_user_role_id_seq'::regclass);
SELECT pg_catalog.setval('user_roles_user_role_id_seq', 3, true);

ALTER TABLE ONLY user_roles ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id);
ALTER TABLE ONLY user_roles ADD CONSTRAINT user_roles_role_username_key UNIQUE (role, username);
ALTER TABLE ONLY user_roles ADD CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users(username);



CREATE TABLE profile_roles (
    profile_role_id integer NOT NULL,
    role character varying(45) NOT NULL,
    profile character varying(45) NOT NULL
);
ALTER TABLE profile_roles OWNER TO postgres;

CREATE SEQUENCE profile_roles_profile_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE profile_roles_profile_role_id_seq OWNER TO postgres;
ALTER SEQUENCE profile_roles_profile_role_id_seq OWNED BY profile_roles.profile_role_id;
ALTER TABLE ONLY profile_roles ALTER COLUMN profile_role_id SET DEFAULT nextval('profile_roles_profile_role_id_seq'::regclass);
SELECT pg_catalog.setval('profile_roles_profile_role_id_seq', 3, true);

ALTER TABLE ONLY profile_roles ADD CONSTRAINT profile_roles_pkey PRIMARY KEY (profile_role_id);
ALTER TABLE ONLY profile_roles ADD CONSTRAINT profile_roles_role_profile_key UNIQUE (role, profile);
--ALTER TABLE ONLY profile_roles ADD CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users(username);








REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


COPY profile_roles(profile, role) FROM stdin;
USER	ROLE_USER2
\.

COPY users (username, password, profile, enabled) FROM stdin;
mkyong	123456	USER	1
alex	123456		1
\.

COPY user_roles (user_role_id, username, role) FROM stdin;
1	mkyong	ROLE_USER
2	mkyong	ROLE_ADMIN
3	alex	ROLE_USER
\.
