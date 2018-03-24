package com.jherrild.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author jherrild@expedia.com
 * Created on 3/22/18
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true) private long ID;
    @Column(nullable = false) private Date startTime;
    @Column(nullable = false) private int length;
}
