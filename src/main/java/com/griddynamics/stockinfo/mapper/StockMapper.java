package com.griddynamics.stockinfo.mapper;

import com.griddynamics.stockinfo.dto.StockDto;
import com.griddynamics.stockinfo.entity.Stock;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockDto mapToStock(Stock stock);
    @InheritInverseConfiguration
    Stock mapToStockDto(StockDto stockDto);
}
