package com.griddynamics.stockinfo.service;

import com.griddynamics.stockinfo.client.ExApiExchangeClientImpl;
import com.griddynamics.stockinfo.dto.CompanyDto;
import com.griddynamics.stockinfo.entity.Company;
import com.griddynamics.stockinfo.mapper.CompanyMapper;
import com.griddynamics.stockinfo.mapper.StockMapper;
import com.griddynamics.stockinfo.repository.CustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataProcessingServiceImpl implements DataProcessingService {

    @Value("${service.number-of-companies}")
    private Integer NUMBER_OF_COMPANIES;
    private final List<String> tasks = new CopyOnWriteArrayList<>();
    private AtomicInteger index = new AtomicInteger();

    private final ExApiExchangeClientImpl apiClient;
    private final CompanyMapper companyMapper;
    private final StockMapper stockMapper;
    private final CustomRepository customRepository;

    @Override
    public Mono<Void> processingCompanyData() {
        tasks.clear();
        return apiClient.callToCompanyApi()
                .onErrorContinue((error, obj) -> log.error("error:[{}]", error.getLocalizedMessage()))
                .filter(CompanyDto::isEnabled)
                .take(NUMBER_OF_COMPANIES)
                .map(companyMapper::mapToCompanyDto)
                .map(this::addTask)
                .map(customRepository::save)
                .map(Mono::subscribe)
                .then();
    }

    @Override
    public Mono<Void> processingStockData() {
        return Flux.fromIterable(tasks)
                .flatMap(s -> apiClient.callToStockApi(getTask()))
                .onErrorContinue((error, obj) -> log.error("[{}]", error.getLocalizedMessage()))
                .map(stockMapper::mapToStockDto)
                .map(customRepository::saveStock)
                .map(Mono::subscribe)
                .then();
    }

    private Company addTask(Company company) {
        var uri = apiClient.getStockUri(company.getSymbol());
        tasks.add(uri);
        return company;
    }

    private String getTask() {
        var task = tasks.get(index.getAndIncrement());
        if (index.get() == NUMBER_OF_COMPANIES) index = new AtomicInteger(0);
        return task;
    }
}
