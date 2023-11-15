package com.griddynamics.stockinfo.client;

import com.griddynamics.stockinfo.BaseAbstractTest;
import com.griddynamics.stockinfo.dto.CompanyDto;
import com.griddynamics.stockinfo.dto.StockDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.support.membermodification.MemberModifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

public class ExApiExchangeClientImplTest extends BaseAbstractTest {
    @Value("${api.external.ref-data-url}")
    private String refDataUrl;

    @Value("${api.external.stock-data-url}")
    private String stockPriceUrl;

    @Value("${api.external.token}")
    private String token;

    @InjectMocks
    private ExApiExchangeClientImpl service;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    public void setUp() throws IllegalAccessException {
        MemberModifier.field(ExApiExchangeClientImpl.class, "refDataUrl").set(service, refDataUrl);
        MemberModifier.field(ExApiExchangeClientImpl.class, "stockPriceUrl").set(service, stockPriceUrl);
        MemberModifier.field(ExApiExchangeClientImpl.class, "token").set(service, token);
    }

    @Test
    void whenCallToCompanyApiSuccessful() {
        var companyDto1 = new CompanyDto(1, "symbol1", true);
        var companyDto2 = new CompanyDto(2, "symbol2", false);
        var expected = Flux.just(companyDto1, companyDto2);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(service.getCompanyUri())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(CompanyDto.class)).thenReturn(expected);
        var actual = service.callToCompanyApi();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenCallToStockApiSuccessful() {
        var uri = service.getStockUri("A");
        var stockDto = new StockDto(1L, "symbol", BigDecimal.TEN, BigDecimal.ONE, 5,
                null, "Name");
        var arrayStockDtoS = new StockDto[]{stockDto};
        var mono = Mono.just(arrayStockDtoS);
        var expected = mono.map(s -> s[0]).block();

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(uri)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(StockDto[].class)).thenReturn(mono);
        var actual = service.callToStockApi(uri);

        Assertions.assertEquals(expected, actual.block());
    }
}
