package hu.codecool.elastic.elastic.dto;

import hu.codecool.elastic.elastic.model.News;

import java.util.List;

public class NewsDto {

    private List<News> all;

    public NewsDto(List<News> all) {
        this.all = all;
    }

    public List<News> getAll() {
        return all;
    }
}
