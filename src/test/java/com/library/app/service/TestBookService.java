package com.library.app.service;

import com.library.app.dto.BookDTO;
import com.library.app.dto.GenreDTO;
import com.library.app.dto.ResponseDTO;
import com.library.app.entity.Book;
import com.library.app.entity.Genre;
import com.library.app.exception.DuplicateEntityException;
import com.library.app.repository.GenreRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBookService {

    @Autowired
    private BookService bookService;

    @Autowired
    private GenreRepository genreRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @BeforeAll
    public void uploadGenres(){
        genreRepository.save(new Genre("Fantasy"));
    }

    @Test
    public void saveBook(){
        BookDTO book = new BookDTO("title1","author1", new GenreDTO(1L, "Fantasy"));
        ResponseDTO<BookDTO> savedBook = bookService.save(book);

        assertThat(savedBook.getData()).isNotNull();
        assertThat(savedBook.getData().getId()).isGreaterThanOrEqualTo(0);
        assertThat(savedBook.getData().getAuthor().length()).isBetween(1,50);
        assertThat(savedBook.getData().getTitle().length()).isBetween(1,50);
        assertThat(savedBook.getData().getGenre()).isNotNull();
        assertThat(savedBook.isSuccess()).isTrue();
    }

    @Test
    public void updateBook(){
        GenreDTO genreDTO = new GenreDTO(1L, "Fantasy");
        BookDTO book = new BookDTO("title1","author1", genreDTO);
        bookService.save(book);

        BookDTO  bookToUpdate = new BookDTO("title2","author1", genreDTO);
        ResponseDTO<BookDTO> savedBook = bookService.save(bookToUpdate);

        assertThat(savedBook.getData()).isNotNull();
        assertThat(savedBook.getData().getId()).isGreaterThanOrEqualTo(0);
        assertThat(savedBook.getData().getAuthor().length()).isBetween(1,50);
        assertThat(savedBook.getData().getTitle().length()).isBetween(1,50);
        assertThat(savedBook.getData().getGenre()).isNotNull();
        assertThat(savedBook.isSuccess()).isTrue();
    }

    @Test
    public void findById(){
        GenreDTO genreDTO = new GenreDTO(1L, "Fantasy");
        BookDTO book = new BookDTO("title1","author1", genreDTO);
        BookDTO savedBook = bookService.save(book).getData();

        ResponseDTO<BookDTO> bookResult = bookService.findById(savedBook.getId());

        assertThat(bookResult.getData()).isNotNull();
        assertThat(bookResult.getData().getId()).isGreaterThanOrEqualTo(0);
        assertThat(bookResult.getData().getAuthor().length()).isBetween(1,50);
        assertThat(bookResult.getData().getTitle().length()).isBetween(1,50);
        assertThat(bookResult.getData().getGenre()).isNotNull();
        assertThat(bookResult.isSuccess()).isTrue();

    }

    @Test
    public void  findAll(){
        GenreDTO genreDTO = new GenreDTO(1L, "Fantasy");
        BookDTO book1 = new BookDTO("title1","author1", genreDTO);
        BookDTO book2 = new BookDTO("title2","author1", genreDTO);
        BookDTO book3 = new BookDTO("title3","author1", genreDTO);

        bookService.save(book1);
        bookService.save(book2);
        bookService.save(book3);

        ResponseDTO<List<BookDTO>> results = bookService.findAll(0,10);
        assertThat(results.getData().size()).isEqualTo(3);

    }
    @Test
    public void  deleteById(){
        GenreDTO genreDTO = new GenreDTO(1L, "Fantasy");
        BookDTO book1 = new BookDTO("title1","author1", genreDTO);
        ResponseDTO<BookDTO> bookResult = bookService.save(book1);

        long sizeBeforeDelete = bookService.findAll(0,10).getData().size();
        bookService.deleteById(bookResult.getData().getId());
        long sizeAfterDelete = bookService.findAll(0,10).getData().size();

        assertThat(sizeAfterDelete).isEqualTo(sizeBeforeDelete - 1);
    }

    @Test
    public void saveEmptyBook(){
        BookDTO book = new BookDTO("","author1", new GenreDTO(1L, "Fantasy"));
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(book);
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void saveDuplicateBook(){
        org.junit.jupiter.api.Assertions.assertThrows(DuplicateEntityException.class, () -> {
            BookDTO book = new BookDTO("12545","author1", new GenreDTO(1L, "Fantasy"));
            bookService.save(book);
            bookService.save(book);
        });

    }




}
