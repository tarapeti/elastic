package hu.codecool.elastic.elastic.repository;

import hu.codecool.elastic.elastic.model.News;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticRepository extends ElasticsearchRepository<News, String> {

    List<News> findAll();

    News findByIdLike(String id);

    List<News> findByContentContaining(String text);

    List<News> findByCreatedLike(String date);

    void deleteAll();

    //List<News> findByContentContainingAndCreatedLike(String text, String date);
}
