package com.course.restspring.pontointeligente.repositories;

import com.course.restspring.pontointeligente.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    //Doesn't make the DB stop, since it is a Read Only Operation
    @Transactional(readOnly = true)
    Company findByCnpj(String cnpj);

}
