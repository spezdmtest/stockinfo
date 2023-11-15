package com.griddynamics.stockinfo.service;

import com.griddynamics.stockinfo.BaseAbstractTest;
import com.griddynamics.stockinfo.jobs.AnalyticDataJob;
import com.griddynamics.stockinfo.jobs.ProcessDataJob;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;

public class JobsTest extends BaseAbstractTest {
    @SpyBean
    private AnalyticDataJob analyticDataJob;

    @SpyBean
    private ProcessDataJob processingDataJob;

    @MockBean
    private AnalyticService analyticService;

    @MockBean
    private DataProcessingService dataProcessingService;

    @Test
    public void scheduleOnStartupTest() {
        await()
                .pollDelay(100, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> verify(processingDataJob, atMostOnce()).runProcessingCompanyDataJob());
    }

    @Test
    public void scheduleGetStockDataTest() {
        await()
                .pollDelay(5000, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> verify(processingDataJob, atMostOnce()).runProcessingStockDataJob());
    }

    @Test
    public void scheduleOnGetAnalyticDataTest() {
        await()
                .pollDelay(5000,TimeUnit.MILLISECONDS)
                .untilAsserted(() -> verify(analyticDataJob, atMostOnce()).runAnalyticsJob());
    }

    @Test
    public void callOnStartupTest() {
        when(dataProcessingService.processingCompanyData()).thenReturn(Mono.empty());
        processingDataJob.runProcessingCompanyDataJob();

        verify(dataProcessingService, atLeastOnce()).processingCompanyData();
    }

    @Test
    public void callGetStockDataTest() {
        when(dataProcessingService.processingStockData()).thenReturn(Mono.empty());
        processingDataJob.runProcessingStockDataJob();

        verify(dataProcessingService, atLeastOnce()).processingStockData();
    }

    @Test
    public void callGetAnalyticDataTest() {
        analyticDataJob.runAnalyticsJob();

        verify(analyticService, atLeastOnce()).getTopFiveStockPrice();
        verify(analyticService, atLeastOnce()).getTopFiveDeltaStocksPrice();
    }
 }
