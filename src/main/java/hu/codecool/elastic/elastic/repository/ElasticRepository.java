package hu.codecool.elastic.elastic.repository;

import hu.codecool.elastic.elastic.model.News;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticRepository extends ElasticsearchRepository<News, String> {

    List<News> findAll();

    //@Query({"query": { "query_string" : { "query" : "(coronavirus) OR (gun)", "content" : "content"}}})
    //List<News> findNewsByStringContent(String content);

    List<News> findNewsByContentContains();
}
