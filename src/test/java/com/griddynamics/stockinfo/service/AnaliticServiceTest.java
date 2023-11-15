package com.griddynamics.stockinfo.service;

import com.griddynamics.stockinfo.BaseAbstractTest;
import com.griddynamics.stockinfo.entity.Stock;
import com.griddynamics.stockinfo.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class AnaliticServiceTest extends BaseAbstractTest {

    @SpyBean
    private AnalyticServiceImpl analyticService;

    @MockBean
    private StockRepository stockRepository;

    @Test
    public void whenContextLoadsSuccessful() {
        assertThat(stockRepository).isNotNull();
    }

    @Test
    void whenGetTopFiveStockPriceIsSuccessful() {
        when(stockRepository.findTop5ExpensiveStocks()).thenReturn(Flux.just(new Stock()));
        analyticService.getTopFiveStockPrice();

        verify(stockRepository, atLeastOnce()).findTop5ExpensiveStocks();
    }

    @Test
    void whenGetTopFiveDeltaStockPriceIsSuccessful() {
        when(stockRepository.findTop5HighestDeltaPrice()).thenReturn(Flux.just(new Stock()));
        analyticService.getTopFiveDeltaStocksPrice();

        verify(stockRepository, atLeastOnce()).findTop5HighestDeltaPrice();
    }
}
