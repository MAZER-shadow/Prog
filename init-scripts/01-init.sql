CREATE TABLE IF NOT EXISTS coordinates (
                                           id BIGSERIAL PRIMARY KEY,
                                           x INTEGER NOT NULL,
                                           y BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS person (
                                      id BIGSERIAL PRIMARY KEY,
                                      name TEXT NOT NULL,
                                      height INTEGER,
                                      passport_id TEXT
);

CREATE TABLE IF NOT EXISTS lab_work (
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        coordinates_id BIGINT NOT NULL REFERENCES coordinates(id) ON DELETE CASCADE,
                                        creation_date DATE NOT NULL DEFAULT CURRENT_DATE,
                                        minimal_point DOUBLE PRECISION NOT NULL,
                                        maximum_point FLOAT NOT NULL,
                                        difficulty TEXT NOT NULL,
                                        author_id BIGINT REFERENCES person(id) ON DELETE SET NULL,

                                        CONSTRAINT min_point_check CHECK (minimal_point > 0),
                                        CONSTRAINT max_point_check CHECK (maximum_point IS NULL OR maximum_point > 0)
);