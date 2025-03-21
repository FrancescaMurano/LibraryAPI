package com.library.app.service;

import com.library.app.dto.BookDTO;
import com.library.app.dto.GenreDTO;
import com.library.app.dto.ResponseDTO;
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

@SpringBootTest
@Transactional
@DirtiesContext
public class TestGenreService {

    @Autowired
    private GenreService genreService;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Test
    public void saveGenre(){
        GenreDTO genreDTO = new GenreDTO("horror");
        ResponseDTO<GenreDTO> savedGenre = genreService.save(genreDTO);

        Assertions.assertThat(savedGenre.getData()).isNotNull();
        Assertions.assertThat(savedGenre.getData().getId()).isGreaterThanOrEqualTo(0);
        Assertions.assertThat(savedGenre.getData().getName().length()).isBetween(1,50);
        Assertions.assertThat(savedGenre.isSuccess()).isTrue();
    }

    @Test
    public void updateBook(){
        GenreDTO genreDTO = new GenreDTO("horror", "horror film");
        Long savedId = genreService.save(genreDTO).getData().getId();
        GenreDTO genreToUpdate = new GenreDTO(savedId,"horror", "horror split film");
        ResponseDTO<GenreDTO> savedGenre = genreService.update(savedId, genreToUpdate);

        Assertions.assertThat(savedGenre.getData()).isNotNull();
        Assertions.assertThat(savedGenre.getData().getId()).isGreaterThanOrEqualTo(0);
        Assertions.assertThat(savedGenre.getData().getName().length()).isBetween(1,50);
        Assertions.assertThat(savedGenre.getData().getDescription().length()).isLessThan(50);
        Assertions.assertThat(savedGenre.isSuccess()).isTrue();
    }

    @Test
    public void findById(){
        GenreDTO genreDTO = new GenreDTO("horror", "horror film");
        ResponseDTO<GenreDTO> savedGenre = genreService.findById(genreService.save(genreDTO).getData().getId());

        Assertions.assertThat(savedGenre.getData()).isNotNull();
        Assertions.assertThat(savedGenre.getData().getId()).isGreaterThanOrEqualTo(0);
        Assertions.assertThat(savedGenre.isSuccess()).isTrue();
    }

    @Test
    public void  findAll(){
        GenreDTO genreDTO1 = new GenreDTO("horror", "horror film");
        GenreDTO genreDTO2 = new GenreDTO("fantasy", "fantasy film");
        GenreDTO genreDTO3 = new GenreDTO("drama", "drama film");

        genreService.save(genreDTO1);
        genreService.save(genreDTO2);
        genreService.save(genreDTO3);

        ResponseDTO<List<GenreDTO>> results = genreService.findAll(0,10);
        Assertions.assertThat(results.getData().size()).isEqualTo(3);
    }

    @Test
    public void  deleteById(){
        GenreDTO genreDTO = new GenreDTO("horror", "horror film");
        ResponseDTO<GenreDTO> savedGenre = genreService.save(genreDTO);

        long sizeBeforeDelete = genreService.findAll(0,10).getData().size();
        genreService.deleteById(savedGenre.getData().getId());
        long sizeAfterDelete = genreService.findAll(0,10).getData().size();

        Assertions.assertThat(sizeAfterDelete).isEqualTo(sizeBeforeDelete - 1);
    }


    @Test
    public void saveEmptyGenre(){
        GenreDTO genreDTO = new GenreDTO("");
        Set<ConstraintViolation<GenreDTO>> violations = validator.validate(genreDTO);
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void saveDuplicateGenre(){
        org.junit.jupiter.api.Assertions.assertThrows(DuplicateEntityException.class, () -> {
            GenreDTO genreDTO = new GenreDTO("Fantasy");
            genreService.save(genreDTO);
            genreService.save(genreDTO);
        });

    }

}
