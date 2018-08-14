package br.com.gb.book.application;

import br.com.gb.book.BookApplication;
import br.com.gb.book.application.transferobject.BookTransferObject;
import br.com.gb.book.domain.entity.Book;
import br.com.gb.book.infrastructure.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
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
    public void shouldFindBookWithSuccess() throws Exception {
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

        HttpEntity<BookTransferObject> entity = new HttpEntity<>(headers);
        ResponseEntity<BookTransferObject> response = restTemplate.getForEntity(URI_API_BOOKS  + "/" + book.getId(), BookTransferObject.class, entity);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(isbn, response.getBody().getIsbn());
        assertEquals(title, response.getBody().getTitle());
        assertEquals(language, response.getBody().getLanguage());
        assertEquals(description, response.getBody().getDescription());
    }

    @Test
    public void shouldFindBookWithReturnNotFound() throws Exception {
        String id = "58920203954901904rjvhjq0130irhfok";

        HttpEntity<BookTransferObject> entity = new HttpEntity<>(headers);
        ResponseEntity<BookTransferObject> response = restTemplate.getForEntity(URI_API_BOOKS  + "/" + id, BookTransferObject.class, entity);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}