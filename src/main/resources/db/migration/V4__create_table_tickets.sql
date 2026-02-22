CREATE TABLE tickets (
                         id BIGSERIAL PRIMARY KEY,
                         event_id BIGINT NOT NULL,
                         status VARCHAR(20) NOT NULL,
                         CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES event(id)
);