package com.library.app.mapper;

import com.library.app.dto.GenreDTO;
import com.library.app.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre toEntity(GenreDTO gDTO);
    GenreDTO toDTO(Genre genre);
}
