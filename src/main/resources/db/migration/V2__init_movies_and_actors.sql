create table t_actor (
                    id BIGSERIAL PRIMARY KEY,
                    full_name VARCHAR(255),
                    birth_date DATE
);

create table t_movie (
                    id BIGSERIAL PRIMARY KEY,
                    title VARCHAR(255) NOT NULL,
                    release_year INTEGER,
                    duration_minutes INTEGER,
                    description TEXT,
                    average_rating DOUBLE PRECISION DEFAULT 0.0
);

create table t_movies_actors (
                    movie_id BIGINT NOT NULL,
                    actor_id BIGINT NOT NULL,
                    PRIMARY KEY (movie_id, actor_id),
                    CONSTRAINT fk_movies_actors_movie FOREIGN KEY (movie_id) REFERENCES t_movie(id) ON DELETE CASCADE,
                    CONSTRAINT fk_movies_actors_actor FOREIGN KEY (actor_id) REFERENCES t_actor(id) ON DELETE CASCADE
);