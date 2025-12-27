INSERT INTO t_permission (name) VALUES ('ROLE_ADMIN');
INSERT INTO t_permission (name) VALUES ('ROLE_USER');

INSERT INTO t_user (username, email, password)
VALUES ('admin', 'admin@cinema.com', '$2a$12$1tvhJpmKMjPUgjIv7tJPueFOJHynEIcHWXI9S/vclYK//ktpv28s.');

INSERT INTO t_user_permissions (user_id, permissions_id) VALUES (1, 1);

INSERT INTO t_actor (full_name, birth_date) VALUES ('Christopher Nolan', '1970-07-30');
INSERT INTO t_movie (title, release_year, duration_minutes, description)
VALUES ('Inception', 2010, 148, 'A thief who steals corporate secrets through the use of dream-sharing technology.');