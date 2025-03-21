package com.library.app.repository;

import com.library.app.AppApplication;
import com.library.app.entity.Book;
import com.library.app.entity.Genre;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = AppApplication.class)
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class TestBookRepository {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeAll
    public void uploadGenres(){
        genreRepository.save(new Genre("Fantasy"));
    }

    @Test
    public void saveBook(){
        Book book = new Book("title1","author1", new Genre(1L));
        Book savedBook = bookRepository.save(book);

        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isGreaterThanOrEqualTo(0);
        Assertions.assertThat(savedBook.getAuthor().length()).isBetween(1,50);
        Assertions.assertThat(savedBook.getTitle().length()).isBetween(1,50);
        Assertions.assertThat(savedBook.getGenre()).isNotNull();
    }

    @Test
    public void updateBook(){
        Book book = new Book("title1","author1", new Genre(1L));
        bookRepository.save(book);

        Book bookToUpdate = new Book("title2","author1", new Genre(1L));
        Book updatedBook = bookRepository.save(bookToUpdate);

        Assertions.assertThat(updatedBook).isNotNull();
        Assertions.assertThat(updatedBook.getId()).isGreaterThanOrEqualTo(0);
        Assertions.assertThat(updatedBook.getAuthor().length()).isBetween(1,50);
        Assertions.assertThat(updatedBook.getTitle().length()).isBetween(1,50);
        Assertions.assertThat(updatedBook.getGenre()).isNotNull();
    }

    @Test
    public void findBookById(){
        Book book = new Book("title1","author1", new Genre(1L));
        Book savedBook = bookRepository.save(book);

        Assertions.assertThat(bookRepository.findById(savedBook.getId())).isNotNull();
    }


    @Test
    public void deleteBook(){
        Book book = new Book("title1","author1", new Genre(1L));
        Book savedBook = bookRepository.save(book);
        bookRepository.deleteById(savedBook.getId());
        Assertions.assertThat(bookRepository.findAll()).isEmpty();
    }

    @Test
    public void findByTitleAndAuthor(){
        Book book = new Book("title1","author1", new Genre(1L));
        bookRepository.save(book);

        Assertions.assertThat(bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor())).isPresent();
    }

    @Test
    public void saveNegativeBook(){
        org.junit.jupiter.api.Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            bookRepository.save(new Book(null,"author1", new Genre(1L)));
        });

        org.junit.jupiter.api.Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            bookRepository.save( new Book("title1",null, new Genre(1L)));
        });

        org.junit.jupiter.api.Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            bookRepository.save( new Book("title1",null, null));
        });
    }

}
