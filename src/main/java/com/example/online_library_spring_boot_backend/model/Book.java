package com.example.online_library_spring_boot_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title is required")
    @Size(min = 1, message = "Title cannot be blank")
    private String title;

    @NotNull(message = "Author is required")
    @Size(min = 1, message = "Author cannot be blank")
    private String author;

    @Size(min=1, message="ISBN is required")
    private String isbn;

    private int publicationYear;

    @Size(max=10000, message="Description must be no more than 10000 characters.")
    private String description;
}
