CREATE TABLE event (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       location VARCHAR(255) NOT NULL,
                       event_date TIMESTAMP NOT NULL,
                       capacity INTEGER NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
