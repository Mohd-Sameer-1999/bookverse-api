package com.bookverse.bookverseApi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private double price;
    private int stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
