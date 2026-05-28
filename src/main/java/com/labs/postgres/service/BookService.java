package com.labs.postgres.service;

import com.labs.postgres.entity.Book;
import com.labs.postgres.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public void createBook(Book book) {
        bookRepository.save(book);
    }

    public Book getBookById(String id) {
        Integer userId = Integer.valueOf(id);
        return bookRepository.findById(userId).orElse(new Book());
    }
}
