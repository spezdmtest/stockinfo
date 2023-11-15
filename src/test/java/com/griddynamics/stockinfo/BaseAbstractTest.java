package com.griddynamics.stockinfo;

import com.griddynamics.stockinfo.client.ExApiExchangeClientImpl;
import com.griddynamics.stockinfo.mapper.CompanyMapper;
import com.griddynamics.stockinfo.mapper.StockMapper;
import com.griddynamics.stockinfo.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(properties = "scheduling.enabled=false")
@TestConfiguration
public abstract class BaseAbstractTest {

    @MockBean
    public ExApiExchangeClientImpl apiClient;

    @Autowired
    public CompanyMapper companyMapper;

    @Autowired
    public StockMapper stockMapper;

    @MockBean
    public CustomRepository customRepository;
}
