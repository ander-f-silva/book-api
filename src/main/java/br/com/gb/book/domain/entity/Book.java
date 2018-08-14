package br.com.gb.book.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Builder
@Document
public class Book implements Serializable {
    private static final long serialVersionUID = 3734249753651254772L;

    @Id
    private String id;

    private String title;

    private String description;

    private String isbn;

    private String language;

}
