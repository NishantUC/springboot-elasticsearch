package com.course.springbootelasticsearch.repository.ElasticSearchTemplate;

import com.course.springbootelasticsearch.model.EcommerceData;
import com.course.springbootelasticsearch.model.EcommerceDataEntity;
import com.course.springbootelasticsearch.repository.AccessStrategy;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository
@Log4j2
@ConditionalOnProperty(name= "data.access.strategy", havingValue = "elasticSearchTemplate")
public class ElasticSearchRestTemplateConfig implements AccessStrategy {

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticSearchRestTemplateConfig(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public EcommerceData findById(String id) {
        Criteria criteria = new Criteria("_id").is(id);
        Query query = new CriteriaQuery(criteria);
        SearchHits<EcommerceDataEntity> searchHits = elasticsearchTemplate.search(query, EcommerceDataEntity.class);
        return searchHits.hasSearchHits() ? searchHits.stream().map(searchHit -> mappingEcommerceData(searchHit)).findFirst().get() : null;
    }

    public EcommerceData mappingEcommerceData(SearchHit<EcommerceDataEntity> searchHit) {
        EcommerceDataEntity content = searchHit.getContent();
        return new EcommerceData()
                .country(content.getCountry())
                .description(content.getDescription())
                .quantity(BigDecimal.valueOf(content.getQuantity()))
                .invoiceDate(content.getInvoiceDate())
                .invoiceNo(content.getInvoiceNumber())
                .unitPrice(content.getUnitPrice())
                .stockCode(content.getStockCode());
    }
}
