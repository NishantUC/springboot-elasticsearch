package com.course.springbootelasticsearch.service;

import com.course.springbootelasticsearch.model.EcommerceData;
import com.course.springbootelasticsearch.repository.AccessStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcommerceDataServiceImpl implements EcommerceDataService{

    private final AccessStrategy accessStrategy;

    public EcommerceDataServiceImpl(AccessStrategy accessStrategy) {
        this.accessStrategy = accessStrategy;
    }

    @Override
    public EcommerceData findById(String country) {
        return accessStrategy.findById(country);
    }

    @Override
    public List<EcommerceData> findByCountry(String country) {
        return accessStrategy.findByCountry(country);
    }
}
