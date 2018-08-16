package br.com.gb.book.insfrastructure.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class BookCrawler {
    private static final String URL_BOOKS ="https://kotlinlang.org/docs/books.html";

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

    private Map<String, String> mapLinks = new LinkedHashMap<>();

    public void parser() throws Exception {
        Document document = Jsoup.connect(URL_BOOKS).get();

        List<String> arrayTitles = convertElementsIntoList(document.getElementsByTag(TAG_H2));
        List<String> arrayLanguages = convertElementsIntoList(document.getElementsByClass(EVENT_LANG));

        Elements descriptions = document.getElementsByTag(TAG_P);

        Map<String, StringBuffer> mapDescription = new LinkedHashMap<>();
        AtomicReference<String> atomicTitle = new AtomicReference<>(SPACE);

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

        for (int i = 0; i < time; i++) {
           System.out.println("title: " + arrayTitles.get(i) +
                              "description: " + mapDescription.get(arrayTitles.get(i)) +
                              "IBNS: " + mapIbns.get(arrayTitles.get(i)) +
                              "lang: " + arrayLanguages.get(i));
        }
    }

    private List<String> convertElementsIntoList(Elements elements) {
        return elements.stream()
                        .map(t -> t.text())
                        .collect(Collectors.toCollection(LinkedList::new));

    }
}
