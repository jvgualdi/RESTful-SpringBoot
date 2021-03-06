package com.course.restspring.repositories;

import com.course.restspring.pontointeligente.entities.Company;
import com.course.restspring.pontointeligente.repositories.CompanyRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    private static final String CNPJ = "5146365000100";

    @Before
    public void setUp() throws  Exception{
        Company company = new Company();
        company.setRazaoSocial("Company de Exemplo");
        company.setCnpj(CNPJ);
        this.companyRepository.save(company);
    }

    @After
    public final void tearDown(){
        this.companyRepository.deleteAll();
    }

    @Test
    public void testFindByCnpj() {
        Company company = this.companyRepository.findByCnpj(CNPJ);

        assertEquals(CNPJ, company.getCnpj());
    }

}
