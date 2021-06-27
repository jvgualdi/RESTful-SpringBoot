package com.course.restspring.services;


import com.course.restspring.pontointeligente.entities.Employee;
import com.course.restspring.pontointeligente.repositories.EmployeeRepository;
import com.course.restspring.pontointeligente.services.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Before
    public void setUp(){
        BDDMockito.given(this.employeeRepository.save(Mockito.any(Employee.class))).willReturn(new Employee());
        //BDDMockito.given(this.employeeRepository.findById(Mockito.anyLong())).willReturn(new Optional<Employee>());
        BDDMockito.given(this.employeeRepository.findByEmail(Mockito.anyString())).willReturn(new Employee());
        BDDMockito.given(this.employeeRepository.findByCpf(Mockito.anyString())).willReturn(new Employee());
    }

    @Test
    public void testFindEmployeeByEmail(){
        Optional<Employee> employee = this.employeeService.findEmplyeeByEmail("email@email.com");
        assertTrue(employee.isPresent());
    }

    @Test
    public void testFindEmployeeByCpf(){
        Optional <Employee> employee = this.employeeService.findEmployeeByCpf("12345678900");
        assertTrue(employee.isPresent());
    }

    @Test
    public void testInsertEmployee(){
        Employee testEmployee = this.employeeService.insert(new Employee());
        assertNotNull(testEmployee);
    }


}
