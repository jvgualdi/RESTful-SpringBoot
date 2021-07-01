package com.course.restspring.repositories;


import com.course.restspring.pontointeligente.entities.Company;
import com.course.restspring.pontointeligente.entities.Employee;
import com.course.restspring.pontointeligente.enums.PerfilEnum;
import com.course.restspring.pontointeligente.repositories.CompanyRepository;
import com.course.restspring.pontointeligente.repositories.EmployeeRepository;
import com.course.restspring.pontointeligente.utils.PasswordUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private static final String EMAIL = "email@email.com";
    private static final String CPF = "24291173474";

    @Before
    public void setUp() throws Exception{
        Company company = this.companyRepository.save(obtainCompanyData());
        this.employeeRepository.save(obtainEmployeeData(company));
    }

    @After
    public final void tearDown(){
        this.companyRepository.deleteAll();
    }

    @Test
    public void testFindEmployeeByEmail(){
        Employee employee = this.employeeRepository.findByEmail(EMAIL);

        assertEquals(EMAIL, employee.getEmail());
    }

    @Test
    public void testFindEmployeeByCPF(){
        Employee employee = this.employeeRepository.findByCpf(CPF);

        assertEquals(CPF, employee.getCpf());
    }

    @Test
    public void testFindEmployeeByCPFOrEmail(){
        Employee employee = this.employeeRepository.findByCpfOrEmail(CPF, EMAIL);

        assertNotNull(employee);
    }

    @Test
    public void testFindEmployeeByCPFOrInvalidEmail(){
        Employee employee = this.employeeRepository.findByCpfOrEmail(CPF, "email@invalid.com");

        assertNotNull(employee);
    }

    @Test
    public void testFindEmployeeByInvalidCPFOrEmail(){
        Employee employee = this.employeeRepository.findByCpfOrEmail("12345678901", EMAIL);

        assertNotNull(employee);
    }

    private Employee obtainEmployeeData(Company company) throws NoSuchAlgorithmException {
        Employee employee = new Employee();
        employee.setNome("Employee1");
        employee.setPerfil(PerfilEnum.ROLE_USUARIO);
        employee.setSenha(PasswordUtils.generatesBCrypt("123456"));
        employee.setCpf(CPF);
        employee.setEmail(EMAIL);
        employee.setCompany(company);
        return employee;
    }

    private Company obtainCompanyData(){
        Company company = new Company();
        company.setRazaoSocial("Example company");
        company.setCnpj("51463645000100");
        return company;

    }

}
