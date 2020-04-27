package hu.codecool.elastic.elastic.service;


import hu.codecool.elastic.elastic.model.News;
import hu.codecool.elastic.elastic.repository.ElasticRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Service
public class SimpleNewsService implements NewsService {

    @Autowired
    private ElasticRepository newsRepository;

    private final String BASE_URL = "https://www.bbc.com/news/10628494";

    @Override
    public int getHtml() throws IOException, ParseException {
        Document doc = Jsoup.connect(BASE_URL).get();

        Elements topics = doc.select("div.story-body > div:nth-child(6) > .links-list > li > a");

        //removing "Top Stories" because articles under it appear under other articles, so it would result in inserting duplicated rows into db
        topics.remove(topics.first());

        List<String> topicNames = getTopicNames(topics, doc);

        List<String> urls = getUrls(topics);

        List<News> all = new ArrayList<>();

        for (String url : urls) {
            Document subDoc = Jsoup.connect(url).get();

            Elements created = subDoc.select("item > pubDate");
            List<String> dates = fetchElemets(created);

            Elements titles = subDoc.select("item > title");
            List<String> titlesUnderMainTopic = fetchElemets(titles);

            Elements links = subDoc.select("item > link");
            List<String> linksUnderMainTopic = fetchElemets(links);

            List<String> contents = fetchContents(linksUnderMainTopic);

            List<News> onUrl = fetchNewsOnURL(titlesUnderMainTopic, contents, dates);

            all.addAll(onUrl);
        }
        newsRepository.saveAll(all);
        return all.size();
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }


    private List<String> fetchContents(List<String> linksUnderMainTopic) throws IOException {
        List<String> contents = new ArrayList<>();
        for (String link : linksUnderMainTopic) {
            Document contentDoc = Jsoup.connect(link).get();
            Elements paragraphs = contentDoc.select("div.story-body__inner > p");
            String content = paragraphs.text();
            //if it only contains a video then the summary is needed
            if (content.equals("")) {
                Elements summary = contentDoc.select("div.vxp-media__summary > p");
                String summaryText = summary.text();
                if (summaryText.equals("")) {
                    Elements sport = contentDoc.select("div.story-body > p");
                    String sportText = sport.text();
                    contents.add(sportText);
                } else {
                    contents.add(summaryText);
                }
            } else {
                contents.add(content);
            }
        }
        return contents;
    }

    private List<String> getTopicNames(Elements element, Document doc) {
        List<String> topicNames = new ArrayList<>();
        for (Element e : element) {
            topicNames.add(e.text());
        }
        return topicNames;
    }

    private List<String> getUrls(Elements element) {
        List<String> ulrs = new ArrayList<>();
        for (Element e : element) {
            for (Attribute a : e.attributes()) {
                String url = a.getValue();
                ulrs.add(url);
            }
        }
        return ulrs;
    }

    private List<String> fetchElemets(Elements elements) {
        List<String> elementsAsString = new ArrayList<>();
        for (Element e : elements) {
            String elementString = e.text();
            elementsAsString.add(elementString);
        }
        return elementsAsString;
    }

    private List<News> fetchNewsOnURL(List<String> headers, List<String> content, List<String> created) {
        List<News> all = new ArrayList<>();
        for (int i = 0; i < headers.size(); i++) {
            all.add(new News(headers.get(i), content.get(i), created.get(i)));
        }
        return all;
    }
}
