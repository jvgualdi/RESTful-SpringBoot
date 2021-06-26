package com.course.restspring.repositories;

import com.course.restspring.pontointeligente.entities.Empresa;
import com.course.restspring.pontointeligente.repositories.EmpresaRepository;
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
public class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String CNPJ = "5146365000100";

    @Before
    public void setUp() throws  Exception{
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa de Exemplo");
        empresa.setCnpj(CNPJ);
        this.empresaRepository.save(empresa);
    }

    @After
    public final void tearDown(){
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testFindByCnpj() {
        Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);

        assertEquals(CNPJ, empresa.getCnpj());
    }

}
