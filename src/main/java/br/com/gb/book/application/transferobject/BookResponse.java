package br.com.gb.book.application.transferobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse implements Serializable {
    private static final long serialVersionUID = -2925981248275421032L;

    private String id;

    private String title;

    private String description;

    private String isbn;

    private String language;

}
