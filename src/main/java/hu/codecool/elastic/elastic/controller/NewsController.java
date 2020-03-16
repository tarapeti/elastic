package hu.codecool.elastic.elastic.controller;

import hu.codecool.elastic.elastic.model.News;
import hu.codecool.elastic.elastic.repository.ElasticRepository;
import hu.codecool.elastic.elastic.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private ElasticRepository elasticRepository;

    @GetMapping(path = "/start")
    @ResponseBody
    int getAllNews() throws IOException, ParseException {
        return newsService.getHtml();
    }

    @GetMapping(path="/all")
    @ResponseBody
    List<News> getAll(){
        List<News> all = elasticRepository.findAll();
        return all;

    }

    @DeleteMapping(path = "/deleteNews")
    public @ResponseBody
    void deleteNews(@RequestParam int id) {

    }
}
