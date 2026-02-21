package com.gabriel.ticketing.domain.event;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(nullable = false)
    private int capacity;

    protected Event() {
    }

    public Event(String name, String location, LocalDateTime eventDate, int capacity) {
        this.name = name;
        this.location = location;
        this.eventDate = eventDate;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public static interface EventRepository extends JpaRepository<Event, Long> {
    }
}
