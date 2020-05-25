package hu.codecool.elastic.elastic.controller;

import hu.codecool.elastic.elastic.dto.NewsDto;
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
    NewsDto getAll(){
        List<News> all = elasticRepository.findAll();
        return new NewsDto(all);
    }

    @PostMapping(path="/cont")
    @ResponseBody
    List<News> getByCont(@RequestBody String cont){
        List<News> all = elasticRepository.findByContentContaining(cont);
        return all;
    }

    @PostMapping(path="/date")
    @ResponseBody
    List<News> getByDate(@RequestBody String date){
        List<News> all = elasticRepository.findByCreatedLike(date);
        return all;
    }
    @PostMapping(path="/getById")
    @ResponseBody
    News getById(@RequestParam String articleId){
        News byIdLike = elasticRepository.findByIdLike(articleId);
        return byIdLike;
    }

    @DeleteMapping(path="/deleteAll")
    @ResponseBody
    void deleteAll(){
        elasticRepository.deleteAll();
    }
}
