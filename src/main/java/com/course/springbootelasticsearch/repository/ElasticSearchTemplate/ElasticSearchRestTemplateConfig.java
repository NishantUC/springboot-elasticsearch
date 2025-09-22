package com.course.springbootelasticsearch.repository.ElasticSearchTemplate;

import com.course.springbootelasticsearch.model.EcommerceData;
import com.course.springbootelasticsearch.model.EcommerceDataEntity;
import com.course.springbootelasticsearch.repository.AccessStrategy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Repository
@Log4j2
@ConditionalOnProperty(name= "data.access.strategy", havingValue = "elasticSearchTemplate")
public class ElasticSearchRestTemplateConfig implements AccessStrategy {

    private final ElasticsearchTemplate elasticsearchTemplate;

    @Value("${query.builder:NativeQuery}")
    private String queryBuilderApproach;

    public ElasticSearchRestTemplateConfig(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public EcommerceData findById(String id) {
        Criteria criteria = new Criteria("_id").is(id);
        Query query = new CriteriaQuery(criteria);
        SearchHits<EcommerceDataEntity> searchHits = elasticsearchTemplate.search(query, EcommerceDataEntity.class);
        return searchHits.hasSearchHits() ? searchHits.stream().map(searchHit -> mappingEcommerceData(searchHit)).findFirst().orElse(null) : null;
    }

    @Override
    public List<EcommerceData> findByCountry(String country) {
        if(queryBuilderApproach.equalsIgnoreCase("CriteriaQuery"))
            return findByCountryUsingCriteriaQuery(country);
        else
            return findByCountryUsingNativeQuery(country);
    }

    /*
    Building query using Native Query.
    NativeQuery is the class to use when you have a complex query, or a query that cannot be expressed by using the Criteria API, for example when building queries and using aggregates.
    It allows to use all the different co.elastic.clients.elasticsearch._types.query_dsl.Query implementations from the Elasticsearch library therefore named "native".
     */
    public List<EcommerceData> findByCountryUsingNativeQuery(String country) {
        Query query = NativeQuery.builder().withQuery(q -> q.match(m -> m.field("Country").query(country))).build();
        SearchHits<EcommerceDataEntity> searchHits = elasticsearchTemplate.search(query, EcommerceDataEntity.class);
        return searchHits.hasSearchHits() ? searchHits.stream().map(ElasticSearchRestTemplateConfig::mappingEcommerceData).collect(Collectors.toList()) : null;
    }

    /*
    Building query using Criteria Query.
    CriteriaQuery based queries allow the creation of queries to search for data without knowing the syntax or basics of Elasticsearch queries.
    They allow the user to build queries by simply chaining and combining Criteria objects that specify the criteria the searched documents must fulfill.
     */
    public List<EcommerceData> findByCountryUsingCriteriaQuery(String country) {
        Criteria criteria = new Criteria("country").is(country);
        Query query = new CriteriaQuery(criteria);
        SearchHits<EcommerceDataEntity> searchHits = elasticsearchTemplate.search(query, EcommerceDataEntity.class);
        return searchHits.hasSearchHits() ? searchHits.stream().map(ElasticSearchRestTemplateConfig::mappingEcommerceData).collect(Collectors.toList()) : null;
    }

    public static EcommerceData mappingEcommerceData(SearchHit<EcommerceDataEntity> searchHit) {
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
