package com.course.springbootelasticsearch.repository;

import com.course.springbootelasticsearch.model.EcommerceData;

public interface AccessStrategy {

    EcommerceData findById(String id);
}
