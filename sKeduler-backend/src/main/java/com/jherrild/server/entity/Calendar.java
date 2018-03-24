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
import java.util.ArrayList;

/**
 * @author jestenh@gmail.com
 * Created on 3/22/18
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Calendar implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true) private long id;
    @Column(unique = true, nullable = false) private String name;
    @Column(nullable = false) private ArrayList<Event> events;

    public Calendar(String name, ArrayList<Event> events) {
        this.name = name;
        this.events = events;
    }
}
