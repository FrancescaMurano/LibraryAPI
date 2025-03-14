package com.library.app.service;

/*
 * Author: Francesca Murano
 */

import com.library.app.dto.GenreDTO;
import com.library.app.dto.ResponseDTO;
import com.library.app.entity.Genre;
import java.util.List;

public interface GenreService {
    ResponseDTO<GenreDTO> save(Genre genre);
    ResponseDTO<GenreDTO> update(Long id, Genre genre);
    ResponseDTO<GenreDTO> findByName(String name);
    ResponseDTO<GenreDTO> findById(Long id);
    ResponseDTO<List<GenreDTO>> findAll(Integer pageNumber, Integer pageSize);
    ResponseDTO<GenreDTO> deleteById(Long id);

}
