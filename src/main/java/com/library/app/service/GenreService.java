package com.library.app.service;

/*
 * Author: Francesca Murano
 */

import com.library.app.dto.GenreDTO;
import com.library.app.dto.ResponseDTO;
import java.util.List;

public interface GenreService {
    ResponseDTO<GenreDTO> save(GenreDTO genreDTO);
    ResponseDTO<GenreDTO> update(Long id, GenreDTO genreDTO);
    ResponseDTO<GenreDTO> findByName(String name);
    ResponseDTO<GenreDTO> findById(Long id);
    ResponseDTO<List<GenreDTO>> findAll(Integer pageNumber, Integer pageSize);
    ResponseDTO<GenreDTO> deleteById(Long id);
}
