package com.course.springbootelasticsearch.repository.ElasticSearchRepository;

import com.course.springbootelasticsearch.model.EcommerceData;
import com.course.springbootelasticsearch.model.EcommerceDataEntity;
import com.course.springbootelasticsearch.repository.AccessStrategy;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
        log.info("Getting data for a country");
        EcommerceDataEntity ecommerceData = ecommerceDataRepository.findById(id).get();
        return new EcommerceData()
                        .country(ecommerceData.getCountry())
                        .description(ecommerceData.getDescription())
                        .quantity(BigDecimal.valueOf(ecommerceData.getQuantity()))
                        .invoiceDate(ecommerceData.getInvoiceDate())
                        .invoiceNo(ecommerceData.getInvoiceNumber())
                        .unitPrice(ecommerceData.getUnitPrice())
                        .stockCode(ecommerceData.getStockCode());
    }
}
