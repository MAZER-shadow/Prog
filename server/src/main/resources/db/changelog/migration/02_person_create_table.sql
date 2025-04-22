CREATE TABLE IF NOT EXISTS person
(
    id          BIGSERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    height      INTEGER,
    passport_id TEXT
);