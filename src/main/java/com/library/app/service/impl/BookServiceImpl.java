package com.library.app.service.impl;

/*
 * Author: Francesca Murano
 */

import com.library.app.dto.BookDTO;
import com.library.app.dto.GenreDTO;
import com.library.app.dto.ResponseDTO;
import com.library.app.entity.Book;
import com.library.app.entity.Genre;
import com.library.app.exception.DuplicateEntityException;
import com.library.app.mapper.BookMapper;
import com.library.app.mapper.GenreMapper;
import com.library.app.repository.BookRepository;
import com.library.app.repository.GenreRepository;
import com.library.app.service.BookService;
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
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;
    private final GenreMapper genreMapper;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository, GenreRepository genreRepository, BookMapper bookMapper, GenreMapper genreMapper) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.bookMapper = bookMapper;
        this.genreMapper = genreMapper;
    }

    @Transactional
    @Override
    public ResponseDTO<BookDTO> save(BookDTO bookDTO) {
        // check duplicates
        Optional<Book> bookToCheck = bookRepository.findByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor());

        if(bookToCheck.isPresent())
                throw new DuplicateEntityException("Book already exists.");

        Genre genre = findGenre(bookDTO.getGenre().getName());
        bookDTO.setGenre(genreMapper.toDTO(genre));
        Book savedBook = bookRepository.save(bookMapper.toEntity(bookDTO));
        return new ResponseDTO<>(true, "Book successfully created!", bookMapper.toDTO(savedBook));
    }

    @Transactional
    @Override
    public ResponseDTO<BookDTO> update(Long id, BookDTO bookDTO) {
        // check duplicates
        if(bookDTO.getId() == null)
            bookDTO.setId(id);
        if (!Objects.equals(id, bookDTO.getId()) && bookRepository.findByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor()).isPresent())  // Same book with same id could be the equals to the last one
            throw new DuplicateEntityException("Book already exists.");

        findById(id);
        Genre genre = findGenre(bookDTO.getGenre().getName());
        bookDTO.setGenre(genreMapper.toDTO(genre));
        bookDTO.setId(id);
        Book savedBook = bookRepository.save(bookMapper.toEntity(bookDTO));
        return new ResponseDTO<>(true, "Book successfully updated!", bookMapper.toDTO(savedBook));
    }

    @Override
    public ResponseDTO<BookDTO> findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book " + id + " doesn't exists!"));
        BookDTO res = new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), new GenreDTO(book.getGenre().getName()));
        return new ResponseDTO<>(true, null, res);
    }

    @Override
    public ResponseDTO<List<BookDTO>> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Book> books = bookRepository.findAll(pageable);
        List<BookDTO> bookDTOS = books.stream().map(n-> new BookDTO(n.getId(),n.getTitle(),n.getAuthor(),new GenreDTO(n.getGenre().getName()))).toList();
        return new ResponseDTO<>(true, null, bookDTOS);
    }

    @Transactional
    @Override
    public ResponseDTO<BookDTO> deleteById(Long id) {
        findById(id);
        bookRepository.deleteById(id);
        return new ResponseDTO<>(true, "Book " + id + " deleted", null);
    }

    private Genre findGenre(String name){
        Optional<Genre> genreToFind = genreRepository.findByName(name);
        if(genreToFind.isEmpty())
            throw new EntityNotFoundException("This Genre doesn't exists.");
        return genreToFind.get();
    }
    private Genre findGenre(long id){
        Optional<Genre> genreToFind = genreRepository.findById(id);
        if(genreToFind.isEmpty())
            throw new EntityNotFoundException("This Genre doesn't exists.");

        return genreToFind.get();
    }
}
