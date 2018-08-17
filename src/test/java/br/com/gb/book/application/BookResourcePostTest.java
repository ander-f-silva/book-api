package br.com.gb.book.application;

import br.com.gb.book.BookApplication;
import br.com.gb.book.application.transferobject.BookRequest;
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
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookResourcePostTest {
    private static final String URI_API_BOOKS = "/library/api/v1/books";

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void shouldAddBookWithSuccess() {
        String language = "EN";
        String isbn = "9781617293290";
        String name = "Kotlin in Action";
        String description = "Kotlin in Action teaches you to use the Kotlin language for production-quality applications.";

        HttpEntity<BookRequest> entity = new HttpEntity<>(new BookRequest(name,description, isbn, language), headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(URI_API_BOOKS, entity, Void.class);
        String location = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(location.contains("/library/api/v1/books"));
    }

    @Test
    public void shouldAddBookWithFieldNameItsNull() {
        String name = null;
        String language = "EN";
        String isbn = "9781617293290";
        String description = "Kotlin in Action teaches you to use the Kotlin language for production-quality applications.";

        HttpEntity<BookRequest> entity = new HttpEntity<>(new BookRequest(name,description, isbn, language), headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(URI_API_BOOKS, entity, Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldAddBookWithFieldDescriptionItsNull() {
        String language = "EN";
        String description = null;
        String isbn = "9781617293290";
        String name = "Kotlin in Action";

        HttpEntity<BookRequest> entity = new HttpEntity<>(new BookRequest(name,description, isbn, language), headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(URI_API_BOOKS, entity, Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldAddBookWithFieldIsbnItsNull()  {
        String isbn = null;
        String language = "EN";
        String name = "Kotlin in Action";
        String description = "Kotlin in Action teaches you to use the Kotlin language for production-quality applications.";

        HttpEntity<BookRequest> entity = new HttpEntity<>(new BookRequest(name,description, isbn, language), headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(URI_API_BOOKS, entity, Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldAddBookWithFieldLanguageItsNull() {
        String language = null;
        String isbn = "9781617293290";
        String name = "Kotlin in Action";
        String description = "Kotlin in Action teaches you to use the Kotlin language for production-quality applications.";

        HttpEntity<BookRequest> entity = new HttpEntity<>(new BookRequest(name,description, isbn, language), headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(URI_API_BOOKS, entity, Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }



}