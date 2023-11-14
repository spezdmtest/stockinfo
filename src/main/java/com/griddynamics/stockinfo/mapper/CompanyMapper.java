package com.griddynamics.stockinfo.mapper;

import com.griddynamics.stockinfo.dto.CompanyDto;
import com.griddynamics.stockinfo.entity.Company;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto mapToCompany(Company company);

    @InheritInverseConfiguration
    Company mapToCompanyDto(CompanyDto companyDto);
}
