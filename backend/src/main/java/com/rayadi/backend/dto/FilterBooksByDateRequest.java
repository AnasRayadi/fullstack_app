package com.rayadi.backend.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FilterBooksByDateRequest {
    private String startDate;
    private String endDate;
}
