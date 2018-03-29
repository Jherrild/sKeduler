package com.jherrild.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author jherrild@expedia.com
 * Created on 3/22/18
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true) private long id;
    @Column(nullable = false) private long calendarId;
    @Column(nullable = false) private String clientName;
    @Column(nullable = false) private long startTime; //Epoch millis
    @Column(nullable = false) private long endTime; //Epoch millis
    @Column(nullable = false) private long length; //minutes
    @Column() private String description;

    @NoArgsConstructor
    public static class EventBuilder {
        long calendarId;
        String clientName;
        long startTime;
        long endTime;
        long length;

        //Optional Parameters
        String description = "";

        public EventBuilder calendarId(long calendarId) {
            this.calendarId = calendarId;
            return this;
        }

        public EventBuilder clientName(String clientName) {
            this.clientName = clientName;
            return this;
        }

        public EventBuilder startTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public EventBuilder endTime(long endTime) {
            this.endTime = endTime;
            return this;
        }

        public EventBuilder length(int length) {
            this.length = length;
            return this;
        }

        public EventBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }

    private Event(EventBuilder builder) {
        this.calendarId = builder.calendarId;
        this.clientName = builder.clientName;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.length = builder.length;
        this.description = builder.description;
    }
}
