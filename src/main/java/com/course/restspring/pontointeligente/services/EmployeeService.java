package com.course.restspring.pontointeligente.services;

import com.course.restspring.pontointeligente.entities.Employee;
import com.course.restspring.pontointeligente.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional <Employee> findEmployeeById(Long id){
        log.info("Searching for an employee by its' id {}", id);
        //the findOne function is deprecated
        return employeeRepository.findById(id);
    }

    public Optional <Employee> findEmployeeByCpf(String cpf){
        log.info("Searching for an employee by its' cpf {}", cpf);
        return Optional.ofNullable(this.employeeRepository.findByCpf(cpf));
    }

    public Optional <Employee> findEmplyeeByEmail(String email){
        log.info("Searching for an employee by its' email {}", email);
        return Optional.ofNullable(this.employeeRepository.findByEmail(email));
    }

    public Employee insert(Employee employee){
        log.info("Inserting on DB the following employee {}", employee);
        return employeeRepository.save(employee);
    }
}
