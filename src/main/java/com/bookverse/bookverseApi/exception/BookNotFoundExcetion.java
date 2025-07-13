package com.bookverse.bookverseApi.exception;

public class BookNotFoundExcetion extends RuntimeException{
    public BookNotFoundExcetion(Long id) {
        super("Book with ID " + id + " not found");
    }
}
