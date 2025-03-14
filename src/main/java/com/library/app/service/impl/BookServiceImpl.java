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

    private BookRepository bookRepository;
    private GenreRepository genreRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional
    @Override
    public ResponseDTO<BookDTO> save(Book book) {
        // check duplicates
        Optional<Book> bookToCheck = bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor());

        if(bookToCheck.isPresent())
                throw new DuplicateEntityException("Book already exists.");
        Genre genre = findGenre(book.getGenre());
        book.setGenre(genre);
        Book savedBook = bookRepository.save(book);
        BookDTO res = new BookDTO(savedBook.getId(), savedBook.getTitle(), savedBook.getAuthor(), new GenreDTO(savedBook.getGenre().getName()));
        return new ResponseDTO<>(true, "Book successfully created!", res);
    }

    @Transactional
    @Override
    public ResponseDTO<BookDTO> update(Long id, Book book) {
        // check duplicates
        if(book.getId() == null)
            book.setId(id);
        if (!Objects.equals(id, book.getId()) && bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor()).isPresent())  // Same book with same id could be the equals to the last one
            throw new DuplicateEntityException("Book already exists.");

        findById(id);
        Genre genre = findGenre(book.getGenre());
        book.setGenre(genre);
        book.setId(id);
        Book savedBook = bookRepository.save(book);
        BookDTO res = new BookDTO(savedBook.getId(), savedBook.getTitle(), savedBook.getAuthor(), new GenreDTO(savedBook.getGenre().getName()));
        return new ResponseDTO<>(true, "Book successfully updated!", res);
    }

    @Override
    public ResponseDTO<BookDTO> findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book "+ id +"doesn't exists!"));
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

    private Genre findGenre(Genre genre){
        Optional<Genre> genreToFind = Optional.empty();
        if(genre.getId() != null)
            genreToFind = genreRepository.findById(genre.getId());
        else if (genre.getName() != null)
            genreToFind = genreRepository.findByName(genre.getName());
        if(genreToFind.isEmpty())
            throw new EntityNotFoundException("This Genre doesn't exists.");

        return genreToFind.get();
    }
}
