package com.example.online_library_spring_boot_backend;

import com.example.online_library_spring_boot_backend.model.Book;
import com.example.online_library_spring_boot_backend.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
class OnlineLibrarySpringBootBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WireMockServer wireMockServer;

	@BeforeEach
	void setup() {
		bookRepository.deleteAll();
		wireMockServer.resetAll();
	}

	@Test
	void testCreateBook_Success() throws Exception {
		Book book = Book.builder()
				.title("Test Title")
				.author("Test Author")
				.isbn("101010101")
				.publicationYear(2025)
				.description("test description blah blah~~~")
				.build();

		String json = objectMapper.writeValueAsString(book);

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.title").value("Test Title"));
	}

	@Test
	void testCreateBook_ValidationError() throws Exception {
		// Missing title => triggers validation
		Book book = Book.builder()
				.author("Author Only")
				.build();

		String json = objectMapper.writeValueAsString(book);

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Title is required"));
	}

	@Test
	void testGetBook_NotFound() throws Exception {
		mockMvc.perform(get("/books/9999"))
				.andExpect(status().isNotFound());
	}

	@Test
	void testSearchBooks() throws Exception {
		bookRepository.save(Book.builder().title("test title AAA").author("author A").build());
		bookRepository.save(Book.builder().title("test title BBB").author("author B").build());

		mockMvc.perform(get("/books/search?title=AAA"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1)); // only "test title AAA" found
	}

	@Test
	void testAiInsights_Success() throws Exception {
		Book saved = bookRepository.save(
				Book.builder().title("test title CCC").author("author C").build()
		);

		wireMockServer.stubFor(
				com.github.tomakehurst.wiremock.client.WireMock.post(
								com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo("/v1/chat/completions"))
						.willReturn(
								com.github.tomakehurst.wiremock.client.WireMock.aResponse()
										.withStatus(200)
										.withHeader("Content-Type", "application/json")
										.withBody("""
                        {
                            "choices": [
                                {
                                    "message": {
                                        "content": "response message"
                                    }
                                }
                            ]
                        }
                        """)
						)
		);

		mockMvc.perform(get("/books/" + saved.getId() + "/ai-insights"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.aiSummary").value("response message"))
				.andExpect(jsonPath("$.book.title").value("test title CCC"));
	}

}
