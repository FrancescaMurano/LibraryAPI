package com.library.app;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestGenreController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String getJsonGenre(Genre genre) throws JsonProcessingException {
        return objectMapper.writeValueAsString(genre);
    }

    @Test
    @Order(1)
    public void testInsertGenre() throws Exception {
        Genre genre = new Genre("FANTASY");
        genre.setDescription("A genre of speculative fiction.");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/genres")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getJsonGenre(genre)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("FANTASY"));
    }

    @Test
    @Order(2)
    public void testGetGenres() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/genres"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("FANTASY"));
    }

    @Test
    @Order(3)
    public void testGetByIDGenres() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/genres/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("FANTASY"));
    }

    @Test
    @Order(4)
    public void testUpdateGenres() throws Exception {
        Genre genre = new Genre("HORROR");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/genres/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJsonGenre(genre)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("HORROR"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/genres"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("HORROR"));
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

    @Test
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