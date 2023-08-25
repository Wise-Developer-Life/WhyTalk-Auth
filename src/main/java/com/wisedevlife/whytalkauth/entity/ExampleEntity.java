package com.wisedevlife.whytalkauth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ExampleEntity {
    @Id @GeneratedValue private Integer id;
}
