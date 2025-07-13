package com.bookverse.bookverseApi.service;


import com.bookverse.bookverseApi.dto.BookRequestDto;
import com.bookverse.bookverseApi.dto.BookResponseDto;
import com.bookverse.bookverseApi.entity.Book;
import com.bookverse.bookverseApi.exception.BookNotFoundExcetion;
import com.bookverse.bookverseApi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundExcetion(id));
    }

    @Override
    public Book updateBook(Long id, Book book){
        Book exstingBook = getBookById(id);
        exstingBook.setTitle(book.getTitle());
        exstingBook.setAuthor(book.getAuthor());
        exstingBook.setGenre(book.getGenre());
        exstingBook.setPrice(book.getPrice());
        exstingBook.setStock(book.getStock());

        return bookRepository.save(exstingBook);
    }

    @Override
    public String deleteBook(Long id){
        try{
            if(!bookRepository.existsById(id)) {
                throw new BookNotFoundExcetion(id);
            }
            else {
                bookRepository.deleteById(id);
                return "Book deleted successfully";
            }
        } catch (Exception e){
            return e.getMessage();
        }

    }
}
