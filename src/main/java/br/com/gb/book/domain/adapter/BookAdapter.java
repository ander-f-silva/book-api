package br.com.gb.book.domain.adapter;

import br.com.gb.book.application.transferobject.BookTransferObject;
import br.com.gb.book.domain.entity.Book;

public class BookAdapter {

    private Book entity;

    private BookTransferObject to;

    public BookAdapter(BookTransferObject to) {
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

    public BookTransferObject converterTransferObject() {
        String title = entity.getTitle();
        String description = entity.getDescription();
        String isbn = entity.getIsbn();
        String language = entity.getLanguage();
        return new BookTransferObject(title,description, isbn, language);
    }
}
