package com.library.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

/*
 * Author: Francesca Murano
 */

@Entity
@Table(name = "Genre", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 50, message = "Genre's name size must be between 1 and 50")
    @Column(length = 50, nullable = false)
    private String name;

    @Size(max = 50, message = "Description max size 50")
    @Column(length = 50)
    private String description;

    public Genre() {
    }

    public Genre(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public Genre(String name) {
        this.name = name;
    }

    public Genre(String name, String description) {
        this.description = description;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
