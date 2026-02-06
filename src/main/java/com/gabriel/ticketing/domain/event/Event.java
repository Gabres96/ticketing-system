package com.gabriel.ticketing.domain.event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private LocalDateTime eventDate;
    private int capacity;

    protected Event() {
    }

    public Event(String name, String location, LocalDateTime eventDate, int capacity) {
        this.name = name;
        this.location = location;
        this.eventDate = eventDate;
        this.capacity = capacity;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getCapacity() { return capacity; }
}
