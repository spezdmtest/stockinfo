package com.griddynamics.stockinfo.repository;

import com.griddynamics.stockinfo.entity.Company;
import com.griddynamics.stockinfo.entity.Stock;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomRepository {
    Mono<Company> save(Company company);
    Mono<Stock> saveStock(Stock stock);
}
