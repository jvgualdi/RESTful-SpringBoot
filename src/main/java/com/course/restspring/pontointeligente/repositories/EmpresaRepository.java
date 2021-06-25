package com.course.restspring.pontointeligente.repositories;

import com.course.restspring.pontointeligente.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    //Doesn't make the DB stop, since it is a Read Only Operation
    @Transactional(readOnly = true)
    Empresa findByCnpj(String cnpj);

}
