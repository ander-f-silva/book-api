package br.com.gb.book.domain.service;

import br.com.gb.book.application.transferobject.BookListResponse;
import br.com.gb.book.application.transferobject.BookRequest;
import br.com.gb.book.application.transferobject.BookResponse;
import br.com.gb.book.domain.adapter.BookAdapter;
import br.com.gb.book.domain.entity.Book;
import br.com.gb.book.domain.exception.NotFoundException;
import br.com.gb.book.infrastructure.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return new BookAdapter(entity).converterTransferObject();
    }

    public BookListResponse findAll() throws Exception {
        List<Book> entities =  repository.findAll();

        if(entities.isEmpty()) {
           throw new NotFoundException("Books not found");
        }

        List<BookResponse> tos = entities.stream()
                                         .map(e -> new BookAdapter(e).converterTransferObject())
                                         .collect(Collectors.toList());

        return new BookListResponse(Long.valueOf(tos.size()), tos);
    }
}
