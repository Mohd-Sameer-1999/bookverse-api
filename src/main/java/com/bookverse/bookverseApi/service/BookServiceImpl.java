package com.bookverse.bookverseApi.service;


import com.bookverse.bookverseApi.entity.Book;
import com.bookverse.bookverseApi.exception.BookNotFoundExcetion;
import com.bookverse.bookverseApi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;


    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks(int page, int size, String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return bookRepository.findAll(pageable).toList();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundExcetion(id));
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Book exstingBook = getBookById(id);
        exstingBook.setTitle(book.getTitle());
        exstingBook.setAuthor(book.getAuthor());
        exstingBook.setGenre(book.getGenre());
        exstingBook.setPrice(book.getPrice());
        exstingBook.setStock(book.getStock());

        return bookRepository.save(exstingBook);
    }

    @Override
    public String deleteBook(Long id) {
        try {
            if (!bookRepository.existsById(id)) {
                throw new BookNotFoundExcetion(id);
            } else {
                bookRepository.deleteById(id);
                return "Book deleted successfully";
            }
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @Override
    public List<Book> searchBook(String title, String author) {
        List <Book> books = bookRepository.findByAuthorAndTitleContainingIgnoreCase(author, title);
        return books;

    }



}
