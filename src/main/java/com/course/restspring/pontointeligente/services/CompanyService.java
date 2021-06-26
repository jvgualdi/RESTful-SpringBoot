package com.course.restspring.pontointeligente.services;

import com.course.restspring.pontointeligente.entities.Company;
import com.course.restspring.pontointeligente.repositories.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CompanyService {

    private static final Logger log = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyRepository companyRepository;

    public Optional<Company> findByCnpj(String cnpj){
        log.info("Searching for a company with the CNPJ {}", cnpj);
        return Optional.ofNullable(companyRepository.findByCnpj(cnpj));
    }

    public Company insert(Company company){
        log.info("Insert compnay {}", company);
        return this.companyRepository.save(company);
    }

}
