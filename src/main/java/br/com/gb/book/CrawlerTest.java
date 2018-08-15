package br.com.gb.book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CrawlerTest {

    private HashSet<String> links;

    public CrawlerTest() {
        this.links = new HashSet<>();
    }

    public void getPageLinks(String URL) throws IOException {

        Document document = Jsoup.connect(URL).get();

        Elements titles = document.getElementsByTag("h2");
        Elements paragraphs = document.getElementsByTag("p");
        Elements langs = document.getElementsByClass("event-lang");

        int time = titles.size();

        List<String> arrayTitles = titles.stream()
                                        .map(t -> t.text())
                                        .collect(Collectors.toCollection(LinkedList::new));

        List<String> arrayLangs = langs.stream()
                                       .map(t -> t.text())
                                       .collect(Collectors.toCollection(LinkedList::new));







//        for (int i = 0; i <time; i++) {
//           System.out.println("Title: " + arrayTitles.get(i) + " Paragraf: " + arrayParagraphs.get(arrayTitles.get(i)) + " Lang: " + arrayLangs.get(i));
//        }

    }

    public static void main(String[] args) throws IOException {
        new CrawlerTest().getPageLinks("https://kotlinlang.org/docs/books.html");
    }

}
