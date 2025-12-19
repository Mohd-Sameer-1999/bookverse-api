package com.bookverse.bookverseApi.controller.v1;

import com.bookverse.bookverseApi.config.Config;
import com.bookverse.bookverseApi.dto.ApiResponse;
import com.bookverse.bookverseApi.dto.BookRequestDto;
import com.bookverse.bookverseApi.dto.BookResponseDto;
import com.bookverse.bookverseApi.modelMapper.BookMapper;
import com.bookverse.bookverseApi.service.BookServiceImpl;
import com.bookverse.bookverseApi.util.ApiResponses;
import com.bookverse.bookverseApi.util.TraceId;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private Config config;


    @GetMapping()
    @Deprecated
    public ResponseEntity<ApiResponse<List<BookResponseDto>>> getAllBooks(
            @RequestParam(defaultValue = "1") int  page,
            @RequestParam(defaultValue = "10") int  size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        List<BookResponseDto> books = bookService.getAllBooks(page, size, sortBy).stream().map(BookMapper::toDto).toList();
        ApiResponse<List<BookResponseDto>> response = ApiResponses.ok("records fetched", books, TraceId.current());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponseDto>> getBook(@PathVariable Long id) {
        BookResponseDto book = BookMapper.toDto(bookService.getBookById(id));
        ApiResponse<BookResponseDto> response = ApiResponses.ok("record fetched", book, TraceId.current());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<BookResponseDto>> addBook(@RequestBody BookRequestDto book){
        BookResponseDto bookResponseDto = BookMapper.toDto(bookService.addBook(BookMapper.toEntity(book)));
        ApiResponse<BookResponseDto> response = ApiResponses.created("Book added", bookResponseDto, TraceId.current());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponseDto>> updateBook(@PathVariable Long id, @RequestBody BookRequestDto book){
        BookResponseDto bookResponseDto = BookMapper.toDto(bookService.updateBook(id, BookMapper.toEntity(book)));
        ApiResponse<BookResponseDto> response = ApiResponses.ok("Book updated", bookResponseDto, TraceId.current());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        ApiResponse<String> response = ApiResponses.ok("Book deleted", bookService.deleteBook(id), TraceId.current());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping("search")
    public ResponseEntity<ApiResponse<List<BookResponseDto>>> searchBook(@RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "") String author) {
        List<BookResponseDto> books = new ArrayList<>();
        bookService.searchBook(title, author).forEach(book -> books.add(BookMapper.toDto(book)));
        ApiResponse<List<BookResponseDto>> response = ApiResponses.ok("records fetched", books, TraceId.current());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
