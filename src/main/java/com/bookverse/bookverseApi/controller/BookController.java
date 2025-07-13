package com.bookverse.bookverseApi.controller;

import com.bookverse.bookverseApi.dto.BookRequestDto;
import com.bookverse.bookverseApi.dto.BookResponseDto;
import com.bookverse.bookverseApi.entity.Book;
import com.bookverse.bookverseApi.modelMapper.BookMapper;
import com.bookverse.bookverseApi.service.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookMapper bookMapper;

    @GetMapping()
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> books = bookService.getAllBooks().stream().map(BookMapper::toDto).toList();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        BookResponseDto book = BookMapper.toDto(bookService.getBookById(id));
        return ResponseEntity.ok(book);
    }

    @PostMapping()
    public ResponseEntity<BookResponseDto> addBook(@RequestBody BookRequestDto book){
        BookResponseDto bookResponseDto = BookMapper.toDto(bookService.addBook(BookMapper.toEntity(book)));
        return new ResponseEntity<>(bookResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @RequestBody BookRequestDto book){
        BookResponseDto bookResponseDto = BookMapper.toDto(bookService.updateBook(id, BookMapper.toEntity(book)));
        return new ResponseEntity<>(bookResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        return  ResponseEntity.ok(bookService.deleteBook(id));
    }

}
