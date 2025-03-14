package com.library.app.controller;

/*
 * Author: Francesca Murano
 */

import com.library.app.dto.GenreDTO;
import com.library.app.dto.ResponseDTO;
import com.library.app.entity.Genre;
import com.library.app.service.GenreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GenreController {
    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public ResponseEntity<ResponseDTO<List<GenreDTO>>> getAllGenres(@RequestParam(required = false, defaultValue = "10") @Min(0) @Max(100) Integer pageSize,
                                                                    @RequestParam(required = false, defaultValue = "0") @Min(0) Integer pageNumber){
        return ResponseEntity.ok(genreService.findAll(pageNumber, pageSize));
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<ResponseDTO<GenreDTO>> findGenreById(@PathVariable Long id){
        return ResponseEntity.ok(genreService.findById(id));
    }

    @PostMapping("/genres")
    public ResponseEntity<ResponseDTO<GenreDTO>> saveGenre(@Valid @RequestBody Genre genre){
        return ResponseEntity.ok(genreService.save(genre));
    }

    @PutMapping("/genres/{id}")
    public ResponseEntity<ResponseDTO<GenreDTO>> updateGenre(@PathVariable Long id, @Valid @RequestBody Genre genre){
        return ResponseEntity.ok(genreService.update(id,genre));
    }

    @DeleteMapping("/genres/{id}")
    public ResponseEntity<ResponseDTO<GenreDTO>> deleteGenre(@PathVariable Long id){
        return ResponseEntity.ok(genreService.deleteById(id));
    }
}
