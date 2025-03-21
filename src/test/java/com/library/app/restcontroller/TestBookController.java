package com.library.app.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.app.dto.GenreDTO;
import com.library.app.entity.Book;
import com.library.app.entity.Genre;
import com.library.app.service.BookService;
import com.library.app.service.GenreService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBookController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GenreService genreService;

    @Autowired
    private BookService bookService;

    private String title = "Harry Potter";

    private String getJsonBook(Book book) throws JsonProcessingException {
        return objectMapper.writeValueAsString(book);
    }

    @BeforeAll
    public void insertGenres(){
        genreService.save(new GenreDTO("HORROR"));
        genreService.save(new GenreDTO("FANTASY"));
        genreService.save(new GenreDTO("COMEDY"));
    }

    @Test
    @Order(1)
    public void testInsertBook() throws Exception {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor("J.K.Rowling");
        book.setGenre(new Genre("FANTASY"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getJsonBook(book)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(title));
    }

    @Test
    @Order(2)
    public void testGetBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value(title));
    }

    @Test
    @Order(3)
    public void testGetByIDBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(title));
    }

    @Test
    @Order(4)
    public void testUpdateBooks() throws Exception {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor("Santa Claus");
        book.setGenre(new Genre("HORROR"));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJsonBook(book)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.author").value("Santa Claus"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].genre.name").value("HORROR"));
    }

    @Test
    @Order(5)
    public void testDeleteBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    @Order(6)
    public void testAddBookWithEmptyTitle() throws Exception {
        Book book = new Book();
        book.setTitle("");
        book.setAuthor("J.K. Rowling");
        book.setGenre(new Genre("FANTASY"));
        String jsonBook = getJsonBook(book);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
