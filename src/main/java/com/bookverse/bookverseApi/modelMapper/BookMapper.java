package com.bookverse.bookverseApi.modelMapper;

import com.bookverse.bookverseApi.dto.BookRequestDto;
import com.bookverse.bookverseApi.dto.BookResponseDto;
import com.bookverse.bookverseApi.entity.Book;

public class BookMapper {

    public static Book toEntity(BookRequestDto dto){
        return Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .genre(dto.getGenre())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();

    }

    public static BookResponseDto toDto(Book book){
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .genre(book.getGenre())
                .author(book.getAuthor())
                .price(book.getPrice())
                .stock(book.getStock())
                .build();

    }
}
