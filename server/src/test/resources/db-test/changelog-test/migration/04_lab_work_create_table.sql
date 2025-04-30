CREATE TABLE IF NOT EXISTS lab_work
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(255)     NOT NULL,
    coordinates_id BIGINT           NOT NULL REFERENCES coordinates (id),
    creation_date  DATE             NOT NULL DEFAULT CURRENT_DATE,
    minimal_point  DOUBLE PRECISION NOT NULL,
    maximum_point  FLOAT            NOT NULL,
    difficulty     TEXT             NOT NULL,
    author_id      BIGINT           REFERENCES person (id),
    user_id        BIGINT           REFERENCES users (id)

    CONSTRAINT min_point_check CHECK (minimal_point > 0),
    CONSTRAINT max_point_check CHECK (maximum_point IS NULL OR maximum_point > 0)
);