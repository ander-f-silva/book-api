package br.com.gb.book.application;

import br.com.gb.book.BookApplication;
import br.com.gb.book.application.transferobject.BookListResponse;
import br.com.gb.book.application.transferobject.BookRequest;
import br.com.gb.book.application.transferobject.BookResponse;
import br.com.gb.book.domain.entity.Book;
import br.com.gb.book.infrastructure.repository.BookRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookResourceGetIT {
    private static final String URI_API_BOOKS = "/library/api/v1/books";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository repository;

    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void shouldFindBookWithSuccess() {
        String language = "EN";
        String isbn = "9781617293290";
        String title = "Kotlin in Action";
        String description = "Kotlin in Action teaches you to use the Kotlin language for production-quality applications.";

        Book book = Book.builder()
                        .title(title)
                        .description(description)
                        .isbn(isbn)
                        .language(language)
                        .build();

        book = repository.insert(book);

        HttpEntity<BookResponse> entity = new HttpEntity<>(headers);
        ResponseEntity<BookResponse> response = restTemplate.getForEntity(URI_API_BOOKS  + "/" + book.getId(), BookResponse.class, entity);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody().getId());
        assertEquals(isbn, response.getBody().getIsbn());
        assertEquals(title, response.getBody().getTitle());
        assertEquals(language, response.getBody().getLanguage());
        assertEquals(description, response.getBody().getDescription());
    }

    @Test
    public void shouldFindBookWithReturnNotFound() {
        String id = "58920203954901904rjvhjq0130irhfok";

        HttpEntity<BookRequest> entity = new HttpEntity<>(headers);
        ResponseEntity<BookRequest> response = restTemplate.getForEntity(URI_API_BOOKS  + "/" + id, BookRequest.class, entity);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldFindAllBooksWithSuccess() {
        String languageOne = "EN";
        String isbnOne = "9781617293290";
        String titleOne = "Kotlin in Action";
        String descriptionOne = "Kotlin in Action teaches you to use the Kotlin language for production-quality applications.";

        Book book = Book.builder()
                .title(titleOne)
                .description(descriptionOne)
                .isbn(isbnOne)
                .language(languageOne)
                .build();

        repository.insert(book);

        String languageTwo = "EN";
        String isbnTwo = "9781788474542";
        String titleTwo = "Kotlin Programming By Example";
        String descriptionTwo = "Kotlin Programming By Example takes you through the building blocks of Kotlin, such as functions and classes.";

        book = Book.builder()
                .title(titleTwo)
                .description(descriptionTwo)
                .isbn(isbnTwo)
                .language(languageTwo)
                .build();

        repository.insert(book);

        HttpEntity<BookListResponse> entity = new HttpEntity<>(headers);
        ResponseEntity<BookListResponse> response = restTemplate.getForEntity(URI_API_BOOKS, BookListResponse.class, entity);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(2, response.getBody().getBooks().size());

        assertNotNull(response.getBody().getBooks().get(0).getId());
        assertEquals(isbnOne, response.getBody().getBooks().get(0).getIsbn());
        assertEquals(titleOne, response.getBody().getBooks().get(0).getTitle());
        assertEquals(languageOne, response.getBody().getBooks().get(0).getLanguage());
        assertEquals(descriptionOne, response.getBody().getBooks().get(0).getDescription());

        assertNotNull(response.getBody().getBooks().get(1).getId());
        assertEquals(isbnTwo, response.getBody().getBooks().get(1).getIsbn());
        assertEquals(titleTwo, response.getBody().getBooks().get(1).getTitle());
        assertEquals(languageTwo, response.getBody().getBooks().get(1).getLanguage());
        assertEquals(descriptionTwo, response.getBody().getBooks().get(1).getDescription());
    }

    @Test
    public void shouldFindAllBooksWithReturnNotFound() {
        repository.deleteAll();

        HttpEntity<BookListResponse> entity = new HttpEntity<>(headers);
        ResponseEntity<BookListResponse> response = restTemplate.getForEntity(URI_API_BOOKS, BookListResponse.class, entity);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}