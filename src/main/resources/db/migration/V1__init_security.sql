create table t_permission (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE
);

create table t_user (
                        id BIGSERIAL PRIMARY KEY,
                        username VARCHAR(255) NOT NULL UNIQUE,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL
);

create table t_user_permissions (
                        user_id BIGINT NOT NULL,
                        permissions_id BIGINT NOT NULL,
                        CONSTRAINT fk_user_permissions_user FOREIGN KEY (user_id) REFERENCES t_user(id),
                        CONSTRAINT fk_user_permissions_permission FOREIGN KEY (permissions_id) REFERENCES t_permission(id)
);