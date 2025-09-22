package com.course.springbootelasticsearch.controller;

import com.course.springbootelasticsearch.api.GetTheEcommerceDataApi;
import com.course.springbootelasticsearch.model.EcommerceData;
import com.course.springbootelasticsearch.service.EcommerceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EcommerceDataController implements GetTheEcommerceDataApi {

    @Autowired
    public EcommerceDataService ecommerceDataService;

    @Override
    public ResponseEntity<List<EcommerceData>> getCountryData(String countryId) {
        return ResponseEntity.ok(ecommerceDataService.findByCountry(countryId));
    }

    @Override
    public ResponseEntity<EcommerceData> getECommerceData(String id) {
        return ResponseEntity.ok(ecommerceDataService.findById(id));
    }
}
