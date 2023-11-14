package com.griddynamics.stockinfo.jobs;

import com.griddynamics.stockinfo.service.DataProcessingService;
import com.griddynamics.stockinfo.util.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessDataJob {

    private final DataProcessingService dataProcessingService;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    @Scheduled(fixedDelay = 3600 * 1000, initialDelay = 100)
    public void runProcessingCompanyDataJob() {
        CompletableFuture.supplyAsync(dataProcessingService::processingCompanyData, executor)
                .thenApply(Mono::subscribe)
                .thenAccept(unused -> log.info("Processing data of companies was finished"))
                .join();
    }
    @TrackExecutionTime
    @Scheduled(fixedDelay = 5000, initialDelay = 3000)
    public void runProcessingStockDataJob() {
        CompletableFuture.supplyAsync(dataProcessingService::processingStockData, executor)
                .thenApply(Mono::subscribe)
                .thenAccept(unused -> log.info("Precessing data of stocks was finished"))
                .join();
    }
}
