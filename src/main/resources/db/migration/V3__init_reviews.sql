create table t_review (
                id BIGSERIAL PRIMARY KEY,
                stars INTEGER CHECK (stars >= 1 AND stars <= 10),
                comment TEXT,
                posted_at TIMESTAMP NOT NULL,
                movie_id BIGINT NOT NULL,
                user_id BIGINT NOT NULL,
                CONSTRAINT fk_review_movie FOREIGN KEY (movie_id) REFERENCES t_movie(id) ON DELETE CASCADE,
                CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
);