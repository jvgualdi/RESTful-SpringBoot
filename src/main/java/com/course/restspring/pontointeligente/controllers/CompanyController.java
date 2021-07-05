package com.course.restspring.pontointeligente.controllers;


import com.course.restspring.pontointeligente.dtos.CompanyDto;
import com.course.restspring.pontointeligente.entities.Company;
import com.course.restspring.pontointeligente.response.Response;
import com.course.restspring.pontointeligente.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/")
public class CompanyController {

    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    public CompanyController() {
    }

    @GetMapping(value = "cnpj/{cnpj}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response<CompanyDto>> findByCnpj(@PathVariable("cnpj") String cnpj){
        log.info("Searching for company with the CNPJ: {}", cnpj);
        Response<CompanyDto> response = new Response<CompanyDto>();
        Optional<Company> company = companyService.findByCnpj(cnpj);

        if (!company.isPresent()){
            log.info("Company not found for the CNPJ: {}", cnpj);
            response.getErrors().add("Company not found for the CNPJ: " + cnpj);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(mapCompanyDto(company.get()));
        return ResponseEntity.ok(response);
    }

    private CompanyDto mapCompanyDto (Company company){
        CompanyDto companyDtodto = new CompanyDto();

        companyDtodto.setId(company.getId());
        companyDtodto.setCnpj(company.getCnpj());
        companyDtodto.setSocialReason(company.getRazaoSocial());

        return companyDtodto;
    }

}
