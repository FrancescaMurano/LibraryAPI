package com.library.app.mapper;
import com.library.app.dto.BookDTO;
import com.library.app.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = GenreMapper.class)
public interface BookMapper {
    Book toEntity(BookDTO bookDTO);
    BookDTO toDTO(Book book);
}
