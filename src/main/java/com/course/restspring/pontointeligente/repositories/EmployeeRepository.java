package com.course.restspring.pontointeligente.repositories;

import com.course.restspring.pontointeligente.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

//Doesn't make the DB stop, since it is a Read Only Operation
@Transactional(readOnly = true)
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByCpf(String cpf);

    Employee findByEmail(String email);

    Employee findByCpfOrEmail(String cpf, String email);
}
