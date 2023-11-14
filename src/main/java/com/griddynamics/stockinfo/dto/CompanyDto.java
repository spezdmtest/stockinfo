package com.griddynamics.stockinfo.dto;

import org.springframework.data.annotation.Id;

public record CompanyDto(@Id Integer id, String symbol, Boolean isEnabled) {
}
