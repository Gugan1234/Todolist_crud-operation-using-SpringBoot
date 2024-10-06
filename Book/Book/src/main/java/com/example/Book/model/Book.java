package com.example.Book.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Todolist")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String completed;
    private String createdAt;

}
