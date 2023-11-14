package com.griddynamics.stockinfo.service;

import reactor.core.publisher.Mono;

public interface DataProcessingService {
    Mono<Void> processingCompanyData();
    Mono<Void> processingStockData();
}
