package com.library.app.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.app.entity.Genre;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestGenreController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private final String genreName = "Horror";

    private String getJsonGenre(Genre genre) throws JsonProcessingException {
        return objectMapper.writeValueAsString(genre);
    }

    @Test
    @Order(1)
    public void testInsertGenre() throws Exception {
        Genre genre = new Genre(genreName);
        genre.setDescription("A genre of speculative fiction.");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/genres")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getJsonGenre(genre)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(genreName));
    }

    @Test
    @Order(2)
    public void testGetGenres() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/genres"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value(genreName));
    }

    @Test
    @Order(3)
    public void testGetByIDGenres() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/genres/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(genreName));
    }

    @Test
    @Order(4)
    public void testUpdateGenres() throws Exception {
        Genre genre = new Genre(genreName+"2");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/genres/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJsonGenre(genre)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(genreName+"2"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/genres"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value(genreName+"2"));
    }

    @Test
    @Order(5)
    public void testDeleteGenres() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/genres/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/genres"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test()
    @Order(6)
    public void testAddGenreWithEmptyName() throws Exception {
        Genre genre = new Genre("");
        String jsonBook = getJsonGenre(genre);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
