package br.com.gb.book.application.transferobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookListResponse implements Serializable {
    private static final long serialVersionUID = -4970521683940208980L;

    private Long numberBooks;

    private List<BookResponse> books;
}
