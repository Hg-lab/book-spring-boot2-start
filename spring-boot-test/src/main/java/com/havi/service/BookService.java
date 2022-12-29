package com.havi.service;

import com.havi.domain.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookService {
    List<Book> getBookList();
}
