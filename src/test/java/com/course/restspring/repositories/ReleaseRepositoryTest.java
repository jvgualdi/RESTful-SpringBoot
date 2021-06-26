package com.course.restspring.repositories;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import com.course.restspring.pontointeligente.entities.Employee;
import com.course.restspring.pontointeligente.entities.Release;
import com.course.restspring.pontointeligente.enums.PerfilEnum;
import com.course.restspring.pontointeligente.enums.TipoEnum;
import com.course.restspring.pontointeligente.repositories.ReleaseRepository;
import com.course.restspring.pontointeligente.utils.PasswordUtils;
import com.course.restspring.pontointeligente.entities.Company;
import com.course.restspring.pontointeligente.repositories.CompanyRepository;
import com.course.restspring.pontointeligente.repositories.EmployeeRepository;
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
public class ReleaseRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ReleaseRepository releaseRepository;

    private static Long employeeId;

    @Before
    public void setUp() throws Exception{
        Company company = this.companyRepository.save(obtainCompanyData());

        Employee employee = this.employeeRepository.save(obtainEmployeeData(company));
        this.employeeId = employee.getId();

        this.releaseRepository.save(obtainReleasesData(employee));
        this.releaseRepository.save(obtainReleasesData(employee));
    }

    @After
    public void tearDown() throws Exception{
        this.companyRepository.deleteAll();
    }

    @Test
    public void testFindReleaseByEmployeeId(){
        List<Release> releases = this.releaseRepository.findByEmployeeId(employeeId);

        assertEquals(2, releases.size());
    }

    private Release obtainReleasesData(Employee employee){
        Release release = new Release();
        release.setData(new Date());
        release.setDataCriacao(new Date());
        release.setDataAtualizacao(new Date());
        release.setDescricao("Saída");
        release.setTipo(TipoEnum.INICIO_ALMOCO);
        release.setEmployee(employee);
        release.setLocalizacao("Company");
        return release;
    }

    private Employee obtainEmployeeData(Company company) throws NoSuchAlgorithmException {
        Employee employee = new Employee();
        employee.setNome("Employee1");
        employee.setPerfil(PerfilEnum.ROLE_USUARIO);
        employee.setSenha(PasswordUtils.generatesBCrypt("123456"));
        employee.setCpf("24291073441");
        employee.setEmail("email@email.com");
        employee.setCompany(company);
        employee.setHorasAlmoco(new Float(2));
        employee.setHorasTrabalhadasDia(new Float(8));
        employee.setValorHora(new BigDecimal(80));
        return employee;
    }

    private Company obtainCompanyData(){
        Company company = new Company();
        company.setRazaoSocial("Example company");
        company.setCnpj("51463645000100");
        return company;

    }

}
