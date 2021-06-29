package com.course.restspring.services;

import com.course.restspring.pontointeligente.entities.Company;
import com.course.restspring.pontointeligente.repositories.CompanyRepository;
import com.course.restspring.pontointeligente.services.CompanyService;
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
public class CompanyServiceTest {

    @MockBean
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    private static final String CNPJ = "12345678900000";

    @Before
    public void setUp() throws Exception{
        BDDMockito.given(this.companyRepository.findByCnpj(Mockito.anyString())).willReturn(new Company());
        BDDMockito.given(this.companyRepository.save(Mockito.any(Company.class))).willReturn(new Company());
    }

    @Test
    public void testFindCompanyByCnpj(){
        Optional<Company> company = this.companyService.findByCnpj(CNPJ);
        assertTrue(company.isPresent());
    }

    @Test
    public void testInsertCompany(){
        Company company = this.companyService.insert(new Company());
        assertNotNull(company);
    }

}
