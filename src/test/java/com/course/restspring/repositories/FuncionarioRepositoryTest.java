package com.course.restspring.repositories;


import com.course.restspring.pontointeligente.entities.Empresa;
import com.course.restspring.pontointeligente.entities.Funcionario;
import com.course.restspring.pontointeligente.enums.PerfilEnum;
import com.course.restspring.pontointeligente.repositories.EmpresaRepository;
import com.course.restspring.pontointeligente.repositories.FuncionarioRepository;
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
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String EMAIL = "email@email.com";
    private static final String CPF = "24291173474";

    @Before
    public void setUp() throws Exception{
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        this.funcionarioRepository.save(obterDadosFuncionario(empresa));
    }

    @After
    public final void tearDown(){
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testFindEmployeeByEmail(){
        Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);

        assertEquals(EMAIL, funcionario.getEmail());
    }

    @Test
    public void testFindEmployeeByCPF(){
        Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);

        assertEquals(CPF, funcionario.getCpf());
    }

    @Test
    public void testFindEmployeeByCPFOrEmail(){
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);

        assertNotNull(funcionario);
    }

    @Test
    public void testFindEmployeeByCPFOrInvalidEmail(){
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "email@invalid.com");

        assertNotNull(funcionario);
    }

    @Test
    public void testFindEmployeeByInvalidCPFOrEmail(){
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail("12345678901", EMAIL);

        assertNotNull(funcionario);
    }

    private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Employee1");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.generatesBCrypt("123456"));
        funcionario.setCpf(CPF);
        funcionario.setEmail(EMAIL);
        funcionario.setEmpresa(empresa);
        funcionario.setHorasAlmoco(new Float(2));
        funcionario.setHorasTrabalhadasDia(new Float(8));
        funcionario.setValorHora(new BigDecimal(80));
        return funcionario;
    }

    private Empresa obterDadosEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Example company");
        empresa.setCnpj("51463645000100");
        return empresa;

    }

}
