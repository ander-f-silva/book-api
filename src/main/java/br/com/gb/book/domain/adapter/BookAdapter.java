package br.com.gb.book.domain.adapter;

import br.com.gb.book.application.transferobject.BookRequest;
import br.com.gb.book.application.transferobject.BookResponse;
import br.com.gb.book.domain.entity.Book;

public class BookAdapter {

    private Book entity;

    private BookRequest to;

    public BookAdapter(BookRequest to) {
        this.to = to;
    }

    public BookAdapter(Book entity) {
        this.entity = entity;
    }

    public Book converterEntity() {
        return Book.builder()
                    .title(to.getTitle())
                    .description(to.getDescription())
                    .isbn(to.getIsbn())
                    .language(to.getLanguage())
                    .build();
    }

    public BookResponse converterTransferObject() {
        String id = entity.getId();
        String title = entity.getTitle();
        String description = entity.getDescription();
        String isbn = entity.getIsbn();
        String language = entity.getLanguage();
        return new BookResponse(id, title,description, isbn, language);
    }
}
