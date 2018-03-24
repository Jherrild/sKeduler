package com.jherrild.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @author jestenh@gmail.com
 * Created on 3/22/18
 */
@Entity
public class Calendar implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true) private long id;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private List<Event> events;

    public Calendar(String name, List<Event> events) {
        this.name = name;
        this.events = events;
    }

    protected Calendar() {

    }
}
