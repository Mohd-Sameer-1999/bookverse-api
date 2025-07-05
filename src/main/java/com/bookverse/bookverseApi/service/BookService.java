package com.bookverse.bookverseApi.service;

import com.bookverse.bookverseApi.entity.Book;
import java.util.List;


public interface BookService {

    Book addBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book updateBook(Long id, Book book);
    String deleteBook(Long id);

}
