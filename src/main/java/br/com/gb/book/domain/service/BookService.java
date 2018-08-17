package br.com.gb.book.domain.service;

import br.com.gb.book.application.transferobject.BookRequest;
import br.com.gb.book.application.transferobject.BookResponse;
import br.com.gb.book.domain.adapter.BookAdapter;
import br.com.gb.book.domain.entity.Book;
import br.com.gb.book.domain.exception.NotFoundException;
import br.com.gb.book.infrastructure.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public String add(BookRequest to) {
        BookAdapter adapter = new BookAdapter(to);
        Book entity = repository.insert(adapter.converterEntity());
        return entity.getId();
    }

    public BookResponse find(String id) throws Exception {
        Optional<Book> optional = repository.findById(id);
        Book entity = optional.orElseThrow(() -> new NotFoundException("Book not found"));
        return new BookAdapter(entity).converterTransferObject() ;
    }
}
