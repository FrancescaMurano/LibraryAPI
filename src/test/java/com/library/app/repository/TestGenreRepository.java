package com.library.app.repository;

import com.library.app.AppApplication;
import com.library.app.entity.Genre;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = AppApplication.class)
@DirtiesContext
@Transactional
public class TestGenreRepository {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void saveGenre(){
        Genre genre = new Genre("FANTASY");
        Genre newGenre = genreRepository.save(genre);
        Assertions.assertThat(newGenre).isNotNull();
    }

    @Test
    public void findGenreById(){
        Genre genre = new Genre("FANTASY");
        Genre newGenre = genreRepository.save(genre);
        Assertions.assertThat(genreRepository.findById(newGenre.getId())).isPresent();
    }

    @Test
    public void findAllGenres(){
        Genre genre1 = new Genre("FANTASY1");
        Genre genre2 = new Genre("FANTASY2");
        genreRepository.save(genre1);
        genreRepository.save(genre2);
        Assertions.assertThat(genreRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void findByName(){
        String name = "FANTASY1";
        genreRepository.save(new Genre(name));
        Assertions.assertThat(genreRepository.findByName(name)).isPresent();
    }

    @Test
    public void deleteGenre(){
        Genre savedGenre = genreRepository.save(new Genre("Horror"));
        genreRepository.deleteById(savedGenre.getId());

        Assertions.assertThat(genreRepository.findById(savedGenre.getId())).isEmpty();
    }

    @Test
    public void saveNegativeGenre(){
        org.junit.jupiter.api.Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            genreRepository.save(new Genre(null,null, null));
        });
    }
}
