package com.example.online_library_spring_boot_backend.service;

import com.example.online_library_spring_boot_backend.model.Book;
import com.example.online_library_spring_boot_backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID " + id + " not found"));
        existing.setTitle(bookDetails.getTitle());
        existing.setAuthor(bookDetails.getAuthor());
        existing.setIsbn(bookDetails.getIsbn());
        existing.setPublicationYear(bookDetails.getPublicationYear());
        existing.setDescription(bookDetails.getDescription());
        return bookRepository.save(existing);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String title, String author) {
        if (title != null && author != null) {
            return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
        } else if (title != null) {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (author != null) {
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        }
        return bookRepository.findAll();
    }
}
