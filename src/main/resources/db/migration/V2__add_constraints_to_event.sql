ALTER TABLE event
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE event
    ALTER COLUMN event_date SET NOT NULL;

ALTER TABLE event
    ADD CONSTRAINT event_capacity_positive
        CHECK (capacity > 0);
