package com.example.online_library_spring_boot_backend.controller;

import com.example.online_library_spring_boot_backend.model.Book;
import com.example.online_library_spring_boot_backend.service.AiService;
import com.example.online_library_spring_boot_backend.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final AiService aiService;

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book saved = bookService.createBook(book);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @Valid @RequestBody Book bookDetails) {
        try {
            Book updated = bookService.updateBook(id, bookDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
    ) {
        return ResponseEntity.ok(bookService.searchBooks(title, author));
    }

    @GetMapping("/{id}/ai-insights")
    public ResponseEntity<AiInsightResponse> getAiInsights(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(book -> {
                    String prompt = "Summarize the following book:\n"
                            + "Title: " + book.getTitle() + "\n"
                            + "Description: " + book.getDescription();
                    String aiSummary = aiService.getAiSummary(prompt);
                    return ResponseEntity.ok(new AiInsightResponse(book, aiSummary));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    record AiInsightResponse(Book book, String aiSummary) {}
}