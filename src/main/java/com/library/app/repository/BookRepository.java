package com.library.app.repository;

/*
 * Author: Francesca Murano
 */

import com.library.app.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByTitleAndAuthor(String title, String author);
}
