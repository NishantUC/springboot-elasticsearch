package com.course.springbootelasticsearch.service;

import com.course.springbootelasticsearch.model.EcommerceData;
import java.util.List;

public interface EcommerceDataService {
    EcommerceData findById(String country);
    List<EcommerceData> findByCountry(String country);
}
