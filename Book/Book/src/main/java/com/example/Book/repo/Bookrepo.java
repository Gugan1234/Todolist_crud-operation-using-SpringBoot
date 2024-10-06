package com.example.Book.repo;

import com.example.Book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Bookrepo extends JpaRepository<Book, Long> {
}
