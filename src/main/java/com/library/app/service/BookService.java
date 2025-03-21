package com.library.app.service;

/*
 * Author: Francesca Murano
 */

import com.library.app.dto.BookDTO;
import com.library.app.dto.ResponseDTO;
import com.library.app.entity.Book;
import java.util.List;

public interface BookService {
    ResponseDTO<BookDTO> save(BookDTO bookDTO);
    ResponseDTO<BookDTO> update(Long id, BookDTO bookDTO);
    ResponseDTO<BookDTO> findById(Long id);
    ResponseDTO<List<BookDTO>> findAll(Integer page, Integer size);
    ResponseDTO<BookDTO> deleteById(Long id);
}
