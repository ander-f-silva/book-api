package br.com.gb.book.setup;

import br.com.gb.book.domain.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BookCrawler {

    @Value("${url.books}")
    private String urlBooks;

    public static final String TAG_A = "a";
    public static final String TAG_P = "p";
    public static final String TAG_H2 = "h2";

    public static final String ATTRIBUTE_HREF = "href";

    public static final String REPLACE_SPACE = " ";
    public static final String CONTAINS_ISBN = "ISBN";
    public static final String REPLACE_ISBN_ = "ISBN ";
    public static final String REPLACE_ISBN_13 = "ISBN 13";
    public static final String UNAVAILABLE_VALUE = "Unavailable";

    public static final String SPACE = "";
    public static final String EVENT_LANG = "event-lang";
    public static final String REGEX_ISBN = "(ISBN[-]*(1[03])*[ ]*(: ){0,1})*(([0-9Xx][- ]*){15}|([0-9Xx][- ]*){13}|([0-9Xx][- ]*){10})";

    public List<Book> parser() throws Exception {
        log.info("Start parser");

        Document document = Jsoup.connect(urlBooks).get();

        log.info("Search title and languages");

        List<String> arrayTitles = convertElementsIntoList(document.getElementsByTag(TAG_H2));
        List<String> arrayLanguages = convertElementsIntoList(document.getElementsByClass(EVENT_LANG));

        Elements descriptions = document.getElementsByTag(TAG_P);

        Map<String, String> mapLinks = new LinkedHashMap<>();
        Map<String, StringBuffer> mapDescription = new LinkedHashMap<>();
        AtomicReference<String> atomicTitle = new AtomicReference<>(SPACE);

        log.info("Search descriptions");

        descriptions
            .stream()
            .forEach(d -> {
                  if (!d.children().isEmpty() && !d.children().tagName(TAG_A).isEmpty()) {
                      String title = d.children().tagName(TAG_A).text();
                      String link = d.children().tagName(TAG_A).attr(ATTRIBUTE_HREF);

                      if(arrayTitles.contains(title)) {
                          atomicTitle.set(title);
                          mapDescription.put(atomicTitle.get(), new StringBuffer(d.text()));

                          mapLinks.put(title, link);
                      } else {
                          mapDescription.get(atomicTitle.get()).append(d.text());
                      }
                  } else {
                      mapDescription.get(atomicTitle.get()).append(d.text());
                  }
            });

        log.info("Search ibns");

        Map<String, String> mapIbns = new LinkedHashMap<>();

        mapLinks.entrySet()
                .parallelStream()
                .forEach( l -> {
                    try {
                        Document documentDetails = Jsoup.connect(l.getValue()).get();
                        String html = documentDetails.body().text();

                        if (html.toUpperCase().contains(CONTAINS_ISBN)) {
                            Pattern pattern  = Pattern.compile(REGEX_ISBN);
                            Matcher matcher = pattern.matcher(html);
                            if(matcher.find()){
                                String value = matcher.group(0);
                                value = value.replaceAll(REPLACE_ISBN_13, SPACE);
                                value = value.replaceAll(REPLACE_ISBN_, SPACE);
                                value = value.replaceAll(REPLACE_SPACE, SPACE);
                                value = value.substring(0, 13);
                                mapIbns.put(l.getKey(), value);
                            } else {
                                mapIbns.put(l.getKey(), UNAVAILABLE_VALUE);
                            }
                        } else {
                            mapIbns.put(l.getKey(), UNAVAILABLE_VALUE);
                        }
                    } catch (IOException e) {
                        mapIbns.put(l.getKey(), UNAVAILABLE_VALUE);
                    }
                });

        int time = arrayTitles.size();

        List<Book> entities = new LinkedList<>();

        for (int i = 0; i < time; i++) {
            String title = arrayTitles.get(i);
            StringBuffer description  = mapDescription.get(arrayTitles.get(i));
            String ibns = mapIbns.get(arrayTitles.get(i));
            String language = arrayLanguages.get(i);

            entities.add(Book.builder()
                             .title(title)
                             .description(String.valueOf(description))
                             .isbn(ibns)
                             .language(language)
                             .build());

        }

        return entities;
    }

    private List<String> convertElementsIntoList(Elements elements) {
        return elements.stream()
                        .map(t -> t.text())
                        .collect(Collectors.toCollection(LinkedList::new));

    }
}
