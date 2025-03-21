package com.library.app.service.impl;

/*
 * Author: Francesca Murano
 */

import com.library.app.dto.GenreDTO;
import com.library.app.dto.ResponseDTO;
import com.library.app.entity.Genre;
import com.library.app.exception.DuplicateEntityException;
import com.library.app.mapper.GenreMapper;
import com.library.app.repository.GenreRepository;
import com.library.app.service.GenreService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper){
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Transactional
    @Override
    public ResponseDTO<GenreDTO> save(GenreDTO genreDTO) {
        Optional<Genre> genreToCheck = genreRepository.findByName(genreDTO.getName());
        if(genreToCheck.isPresent())
            throw new DuplicateEntityException("Genre already exists!");
        Genre savedGenre = genreRepository.save(genreMapper.toEntity(genreDTO));
        return new ResponseDTO<>(true,null, genreMapper.toDTO(savedGenre));
    }

    @Transactional
    @Override
    public ResponseDTO<GenreDTO> update(Long id, GenreDTO genreDTO) {
        Optional<Genre> genreToCheck = genreRepository.findByName(genreDTO.getName());
        if(genreToCheck.isPresent() && !Objects.equals(id, genreDTO.getId())) // check duplicates
            throw new DuplicateEntityException("Genre already exists!");
        genreDTO.setId(id);
        Genre savedGenre = genreRepository.save(genreMapper.toEntity(genreDTO));
        return new ResponseDTO<>(true,"Genre "+ id + " updated!", genreMapper.toDTO(savedGenre));
    }

    @Override
    public ResponseDTO<GenreDTO> findByName(String name) {
        Genre genreToCheck = genreRepository.findByName(name).orElseThrow(()->new EntityNotFoundException("Genre with name " + name + " not exists"));
        GenreDTO genreDTO = new GenreDTO(genreToCheck.getId(),genreToCheck.getName(), genreToCheck.getDescription());
        return new ResponseDTO<>(true,null, genreDTO);
    }

    @Override
    public ResponseDTO<GenreDTO> findById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Genre with id " + id + " not exists"));
        GenreDTO genreDTO = new GenreDTO(genre.getId(),genre.getName(), genre.getDescription());
        return new ResponseDTO<>(true,null, genreDTO);
    }

    @Override
    public ResponseDTO<List<GenreDTO>> findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Genre> genres = genreRepository.findAll(pageable);
        List<GenreDTO> genresDTO = genres.stream().map(n-> new GenreDTO(n.getId(),n.getName(),n.getDescription())).toList();
        return new ResponseDTO<>(true, null, genresDTO);
    }

    @Transactional
    @Override
    public ResponseDTO<GenreDTO> deleteById(Long id) {
        findById(id);
        genreRepository.deleteById(id);
        return new ResponseDTO<>(true, "Genre with " + id + " removed.", null);

    }
}
