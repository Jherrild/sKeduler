package com.jherrild.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author jherrild@expedia.com
 * Created on 3/22/18
 */
@Entity
@AllArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long ID;
    private Date startTime;
    private int length;
}
