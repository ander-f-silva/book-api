package br.com.gb.book.application;

import br.com.gb.book.application.transferobject.BookRequest;
import br.com.gb.book.application.transferobject.BookResponse;
import br.com.gb.book.domain.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping(value = "/library/api/v1/books", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BookResource {

    @Autowired
    private BookService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> add(@Valid @RequestBody BookRequest to) {
        String id = service.add(to);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> find(@PathVariable("id") String id) throws Exception {
        BookResponse to = service.find(id);
        return ResponseEntity.ok(to);
    }
}
