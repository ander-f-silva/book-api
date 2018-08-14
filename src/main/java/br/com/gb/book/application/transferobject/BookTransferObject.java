package br.com.gb.book.application.transferobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTransferObject implements Serializable {
    private static final long serialVersionUID = 8654718517126746109L;

    @NotNull(message = "Field title is required")
    @NotEmpty(message = "Field title is required")
    private String title;

    @NotNull(message = "Field description is required")
    @NotEmpty(message = "Field description is required")
    private String description;

    @NotNull(message = "Field isbn is required")
    @NotEmpty(message = "Field isbn is required")
    @JsonProperty("ISBN")
    private String isbn;

    @NotNull(message = "Field language is required")
    @NotEmpty(message = "Field language is required")
    private String language;

}
