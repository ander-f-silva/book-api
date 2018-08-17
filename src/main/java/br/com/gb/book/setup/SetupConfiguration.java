package br.com.gb.book.setup;

import br.com.gb.book.domain.entity.Book;
import br.com.gb.book.infrastructure.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Slf4j
@Configuration
@Profile("default")
public class SetupConfiguration implements ApplicationRunner {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookCrawler crawler;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("Start loader books.");

        long total = repository.count();

        if (total == 0) {
            log.info("Parse crawler.");
            List<Book> entities  = crawler.parser();
            log.info("Insert books.");
            repository.insert(entities);
            log.info("Success!");
        } else {
            log.warn("Base already has records.");
        }

        log.info("End loader books.");
    }
}
