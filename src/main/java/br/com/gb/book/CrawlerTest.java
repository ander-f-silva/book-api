package br.com.gb.book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class CrawlerTest {

    private HashSet<String> links;

    public CrawlerTest() {
        this.links = new HashSet<>();
    }

    public void getPageLinks(String URL) throws IOException {

        Document document = Jsoup.connect(URL).get();
        Elements titles = document.getElementsByTag("h2");

        List<String> arrayTitles = new LinkedList<>();

        for (Element title : titles) {
            arrayTitles.add(title.text());
        }

        arrayTitles.stream().forEach(s -> System.out.println("Title: " +s));

        Elements paragraphs = document.getElementsByTag("p");

        Map<String, StringBuffer> arrayParagraphs = new LinkedHashMap<>();

        String content = "";

        for (Element paragraph : paragraphs) {
            Elements children = paragraph.children();

            Elements as = children.tagName("a");

            content = as.isEmpty() ? content : as.first().text();

            if (!arrayParagraphs.containsKey(content)) {
                arrayParagraphs.put(content, new StringBuffer(paragraph.text()));
            } else {
                arrayParagraphs.get(content).append(paragraph.text());
            }
        }

        arrayParagraphs.entrySet().stream().forEach(p -> System.out.println("Paragraf: " + p.getValue()));

        Elements links = document.getElementsByClass("event-lang");

        List<String> arrayLinks = new LinkedList<>();

        for (Element link : links) {
            arrayLinks.add(link.text());
        }

        arrayLinks.stream().forEach(l -> System.out.println("Link: " + l));


    }

    public static void main(String[] args) throws IOException {
        new CrawlerTest().getPageLinks("https://kotlinlang.org/docs/books.html");
    }

}
