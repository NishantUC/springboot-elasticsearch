package com.course.springbootelasticsearch.repository.ElasticSearchRepository;

import com.course.springbootelasticsearch.model.EcommerceData;
import com.course.springbootelasticsearch.model.EcommerceDataEntity;
import com.course.springbootelasticsearch.repository.AccessStrategy;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnProperty(name= "data.access.strategy", havingValue = "elasticSearchRepository")
@Service
@Log4j2
public class ElasticsearchRepositoryConfig implements AccessStrategy {

    private final EcommerceDataRepository ecommerceDataRepository;

    public ElasticsearchRepositoryConfig(EcommerceDataRepository ecommerceDataRepository) {
        this.ecommerceDataRepository = ecommerceDataRepository;
    }

    @Override
    public EcommerceData findById(String id) {
        log.info("Retrieving data based on id: {}", id);
        EcommerceDataEntity ecommerceData = ecommerceDataRepository.findById(id).get();
        return getEcommerceData(ecommerceData);
    }

    private static EcommerceData getEcommerceData(EcommerceDataEntity ecommerceData) {
        return new EcommerceData()
                .country(ecommerceData.getCountry())
                .description(ecommerceData.getDescription())
                .quantity(BigDecimal.valueOf(ecommerceData.getQuantity()))
                .invoiceDate(ecommerceData.getInvoiceDate())
                .invoiceNo(ecommerceData.getInvoiceNumber())
                .unitPrice(ecommerceData.getUnitPrice())
                .stockCode(ecommerceData.getStockCode());
    }

    @Override
    public List<EcommerceData> findByCountry(String country) {
        log.info("Retrieving data based on country: {}", country);
        List<EcommerceDataEntity> byCountry = ecommerceDataRepository.findByCountry(country);
        return byCountry.stream().map(ElasticsearchRepositoryConfig::getEcommerceData).collect(Collectors.toList());
    }
}
