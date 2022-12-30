package com.havi;


import com.havi.domain.Book;
import com.havi.repository.BookRepository;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class BookJpaRepository {
    private final static String BOOT_TEST_TITLE = "Spring Boot Test Book";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void Book_Save_Test() {
        Book book = Book.builder()
                .title(BOOT_TEST_TITLE)
                .publishedAt(LocalDateTime.now())
                .build();
        testEntityManager.persist(book);
        assertEquals(bookRepository.findById(book.getIdx()).orElse(null), book);
    }

    @Test
    public void Book_Save_And_FindAll_Test() {
        Book book1 = Book.builder()
                .title(BOOT_TEST_TITLE+"1")
                .publishedAt(LocalDateTime.now())
                .build();
        testEntityManager.persist(book1);

        Book book2 = Book.builder()
                .title(BOOT_TEST_TITLE+"2")
                .publishedAt(LocalDateTime.now())
                .build();
        testEntityManager.persist(book2);

        Book book3 = Book.builder()
                .title(BOOT_TEST_TITLE+"3")
                .publishedAt(LocalDateTime.now())
                .build();
        testEntityManager.persist(book3);

        List<Book> bookList = bookRepository.findAll();
        assertEquals(bookList.size(), 3);
        assertTrue(bookList.contains(book1));
        assertTrue(bookList.contains(book2));
        assertTrue(bookList.contains(book3));

    }

    @Test
    public void Book_Save_And_DeleteAll_Test() {
        Book book1 = Book.builder()
                .title(BOOT_TEST_TITLE+"1")
                .publishedAt(LocalDateTime.now())
                .build();
        testEntityManager.persist(book1);

        Book book2 = Book.builder()
                .title(BOOT_TEST_TITLE+"2")
                .publishedAt(LocalDateTime.now())
                .build();
        testEntityManager.persist(book1);

        Book book3 = Book.builder()
                .title(BOOT_TEST_TITLE+"3")
                .publishedAt(LocalDateTime.now())
                .build();
        testEntityManager.persist(book1);

        bookRepository.deleteAll();

        assertEquals(bookRepository.findAll().size(), 0);

    }

}
