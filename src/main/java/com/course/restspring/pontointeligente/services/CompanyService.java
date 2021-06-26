package com.course.restspring.pontointeligente.services;

import com.course.restspring.pontointeligente.entities.Empresa;
import com.course.restspring.pontointeligente.repositories.EmpresaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CompanyService {

    private static final Logger log = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private EmpresaRepository empresaRepository;

    public Optional<Empresa> findByCnpj(String cnpj){
        log.info("Searching for a company with the CNPJ {}", cnpj);
        return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
    }

    public Empresa insert(Empresa company){
        log.info("Insert compnay {}", company);
        return this.empresaRepository.save(company);
    }

}
