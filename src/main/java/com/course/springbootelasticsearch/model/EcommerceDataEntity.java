package com.course.springbootelasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "ecommerce_data")
public class EcommerceDataEntity {

    @Id
    private String id;

    @Field(type = FieldType.Keyword, name = "Country")
    private String country;

    @Field(type = FieldType.Long, name = "CustomerID")
    private Long customerId;

    @Field(type = FieldType.Text, name = "Description")
    private String description;

    @Field(type = FieldType.Date, pattern = "M/d/yyyy H:m", name = "InvoiceDate")
    private LocalDateTime invoiceDate;

    @Field(type = FieldType.Keyword, name = "InvoiceNo")
    private String invoiceNumber;

    @Field(type = FieldType.Long, name = "Quantity")
    private Long quantity;

    @Field(type = FieldType.Keyword, name = "StockCode")
    private String stockCode;

    @Field(type = FieldType.Double, name = "UnitPrice")
    private Double unitPrice;
}
