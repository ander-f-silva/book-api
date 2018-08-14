package br.com.gb.book.infrastructure.repository;

import br.com.gb.book.domain.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
