package com.griddynamics.stockinfo.service;

import com.griddynamics.stockinfo.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticServiceImpl implements AnalyticService {

    private final StockRepository stockRepository;

    @Override
    public void getTopFiveStockPrice() {
        AtomicInteger atomicInteger = new AtomicInteger();
        CompletableFuture.runAsync(() -> stockRepository.findTop5ExpensiveStocks()
                        .subscribe(stock ->
                                log.info(atomicInteger.incrementAndGet() + " of the top 5 most expensive stocks: price-{} company-{}",
                                        stock.getLatestPrice(), stock.getCompanyName())))
                .join();
    }

    @Override
    public void getTopFiveDeltaStocksPrice() {
        AtomicInteger atomicInteger = new AtomicInteger();
        CompletableFuture.runAsync(() -> stockRepository.findTop5HighestDeltaPrice()
                        .subscribe(stock ->
                                log.info(atomicInteger.incrementAndGet() + " of the 5 fastest growing stocks: change-{}, price-{}, company-{}",
                                        stock.getChange(), stock.getLatestPrice(), stock.getCompanyName())))
                .join();
    }
}
