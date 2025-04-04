package com.library.app.controller;

/*
 * Author: Francesca Murano
 */

import com.library.app.dto.BookDTO;
import com.library.app.dto.ResponseDTO;
import com.library.app.mapper.BookMapper;
import com.library.app.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper){
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<ResponseDTO<List<BookDTO>>> getAllBooks(@RequestParam(required = false, defaultValue = "10") @Min(0) @Max(100) int pageSize,
                                                                  @RequestParam(required = false, defaultValue = "0") @Min(0) int pageNumber){
        return ResponseEntity.ok(bookService.findAll(pageNumber,pageSize));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<ResponseDTO<BookDTO>> findBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping("/books")
    public ResponseEntity<ResponseDTO<BookDTO>> saveBook(@Valid @RequestBody BookDTO bookDTO){
        return ResponseEntity.ok(bookService.save(bookDTO));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<ResponseDTO<BookDTO>> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO){
        return ResponseEntity.ok(bookService.update(id, bookDTO));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<ResponseDTO<BookDTO>> deleteBook(@PathVariable Long id){
        return ResponseEntity.ok(bookService.deleteById(id));
    }
}
