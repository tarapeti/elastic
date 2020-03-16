package hu.codecool.elastic.elastic.service;


import hu.codecool.elastic.elastic.model.News;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface NewsService {

    int getHtml() throws IOException, ParseException;

    List<News> getAllNews();
}
