package com.griddynamics.stockinfo.service;

import com.griddynamics.stockinfo.BaseAbstractTest;
import com.griddynamics.stockinfo.dto.CompanyDto;
import com.griddynamics.stockinfo.dto.StockDto;
import com.griddynamics.stockinfo.entity.Company;
import com.griddynamics.stockinfo.entity.Stock;
import org.junit.jupiter.api.Test;
import org.powermock.api.support.membermodification.MemberModifier;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DataProcessingServiceTest extends BaseAbstractTest {
    @SpyBean
    private DataProcessingService service;
    private List<String> testTasks;

    @Test
    void whenProcessingOfCompanyDataSuccessful() throws IllegalAccessException {
        testTasks = new ArrayList<>();
        MemberModifier.field(DataProcessingServiceImpl.class, "tasks").set(service, testTasks);
        CompanyDto dto = new CompanyDto(1, "symbol", true);
        Company company = new Company(1, "symbol", true);
        Flux<CompanyDto> flux = Flux.just(dto);
        Mono<Company> mono = Mono.just(company);

        when(apiClient.callToCompanyApi()).thenReturn(flux);
        when(customRepository.save(any())).thenReturn(mono);
        var result = service.processingCompanyData();

        result.as(StepVerifier::create).verifyComplete();
        verify(apiClient, times(1)).callToCompanyApi();
        verify(customRepository, times(1)).save(any());
    }

    @Test
    void whenProcessingOfStocksDataSuccessful() throws IllegalAccessException {
        testTasks = List.of("url1", "url2");
        MemberModifier.field(DataProcessingServiceImpl.class, "tasks").set(service, testTasks);
        StockDto dto = new StockDto(1L, "symbol", BigDecimal.TEN, BigDecimal.ONE, 5, null,
                "Name");
        Stock stock = new Stock(1L, "symbol", BigDecimal.TEN, BigDecimal.ONE, 5,null,
                "Name", true);
        Mono<StockDto> monoDto = Mono.just(dto);
        Mono<Stock> mono = Mono.just(stock);

        when(apiClient.callToStockApi(anyString())).thenReturn(monoDto);
        when(customRepository.saveStock(any())).thenReturn(mono);
        var result = service.processingStockData();

        result.as(StepVerifier::create).verifyComplete();
        verify(apiClient, times(2)).callToStockApi(anyString());
        verify(customRepository, times(2)).saveStock(any(Stock.class));
    }
}
