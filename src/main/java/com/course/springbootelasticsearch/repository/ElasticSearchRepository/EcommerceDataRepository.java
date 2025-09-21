package com.course.springbootelasticsearch.repository.ElasticSearchRepository;

import com.course.springbootelasticsearch.model.EcommerceDataEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EcommerceDataRepository extends ElasticsearchRepository<EcommerceDataEntity,String> {
    List<EcommerceDataEntity> findByCountry(String country);
}
