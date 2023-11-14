package com.griddynamics.stockinfo.client;

import com.griddynamics.stockinfo.dto.CompanyDto;
import com.griddynamics.stockinfo.dto.StockDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

public interface ExApiExchangeClient {
    Flux<CompanyDto> callToCompanyApi();
    Mono<StockDto> callToStockApi(String uri);

    URI getCompanyUri();

    String getStockUri(String symbol);
}
