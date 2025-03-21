package com.library.app.dto;

/*
 * Author: Francesca Murano
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

public class GenreDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @Size(min = 1, max = 50, message = "Genre's name size must be between 1 and 50.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    public GenreDTO() {
    }

    public GenreDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public GenreDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public GenreDTO(String name) {
        this.name = name;
    }

    public GenreDTO(Long id) {
        this.id = id;
    }

    public GenreDTO(Long id, String name) {
        this.id = id;
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
        return "GenreDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}