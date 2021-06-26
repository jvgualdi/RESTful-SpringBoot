package com.course.restspring.repositories;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import com.course.restspring.pontointeligente.enums.PerfilEnum;
import com.course.restspring.pontointeligente.enums.TipoEnum;
import com.course.restspring.pontointeligente.utils.PasswordUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.course.restspring.pontointeligente.entities.Empresa;
import com.course.restspring.pontointeligente.entities.Funcionario;
import com.course.restspring.pontointeligente.entities.Lancamento;
import com.course.restspring.pontointeligente.repositories.EmpresaRepository;
import com.course.restspring.pontointeligente.repositories.FuncionarioRepository;
import com.course.restspring.pontointeligente.repositories.LancamentoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    private static Long employeeId;

    @Before
    public void setUp() throws Exception{
        Empresa company = this.empresaRepository.save(obterDadosEmpresa());

        Funcionario employee = this.funcionarioRepository.save(obterDadosFuncionario(company));
        this.employeeId = employee.getId();

        this.lancamentoRepository.save(obterDadosLancamentos(employee));
        this.lancamentoRepository.save(obterDadosLancamentos(employee));
    }

    @After
    public void tearDown() throws Exception{
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testFindReleaseByEmployeeId(){
        List<Lancamento> releases = this.lancamentoRepository.findByFuncionarioId(employeeId);

        assertEquals(2, releases.size());
    }

    private Lancamento obterDadosLancamentos(Funcionario employee){
        Lancamento release = new Lancamento();
        release.setData(new Date());
        release.setDataCriacao(new Date());
        release.setDataAtualizacao(new Date());
        release.setDescricao("Saída");
        release.setTipo(TipoEnum.INICIO_ALMOCO);
        release.setFuncionario(employee);
        release.setLocalizacao("Empresa");
        return release;
    }

    private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Employee1");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.generatesBCrypt("123456"));
        funcionario.setCpf("24291073441");
        funcionario.setEmail("email@email.com");
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
