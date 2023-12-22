package com.rayadi.backend.dto;

import lombok.Data;

@Data
public class FilterBooksRequest {
    private String categoryId;
    private String startDate;
    private String endDate;
}
