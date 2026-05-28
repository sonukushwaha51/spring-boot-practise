package com.labs.postgres.controller;

import com.labs.mysql.entity.User;
import com.labs.postgres.entity.Book;
import com.labs.postgres.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create-book")
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        bookService.createBook(book);
        return new ResponseEntity<>("Book created", HttpStatus.OK);
    }

    @GetMapping("/get-book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(name = "id") String id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

}
