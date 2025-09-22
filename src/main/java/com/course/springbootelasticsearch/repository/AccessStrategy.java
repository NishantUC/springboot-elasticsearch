package com.course.springbootelasticsearch.repository;

import com.course.springbootelasticsearch.model.EcommerceData;
import java.util.List;

public interface AccessStrategy {

    EcommerceData findById(String id);

    List<EcommerceData> findByCountry(String country);
}
