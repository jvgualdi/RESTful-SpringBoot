package com.course.restspring.pontointeligente.controllers;

import com.course.restspring.pontointeligente.dtos.LegalEntityRegistrationDto;
import com.course.restspring.pontointeligente.entities.Company;
import com.course.restspring.pontointeligente.entities.Employee;
import com.course.restspring.pontointeligente.enums.PerfilEnum;
import com.course.restspring.pontointeligente.response.Response;
import com.course.restspring.pontointeligente.services.CompanyService;
import com.course.restspring.pontointeligente.services.EmployeeService;
import com.course.restspring.pontointeligente.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class LegalEntityRegistrationController {

    private static final Logger log = LoggerFactory.getLogger(LegalEntityRegistrationController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    public LegalEntityRegistrationController (){
    }

    @PostMapping(value = "legal-entity-register", consumes = "application/json")
    public ResponseEntity<Response<LegalEntityRegistrationDto>> legalEntityRegistration(@Valid @RequestBody LegalEntityRegistrationDto legalEntityRegistrationDto,
                                                                                        BindingResult result) throws NoSuchAlgorithmException {
        log.info("Registring Legal Entity {}", legalEntityRegistrationDto.toString());
        Response<LegalEntityRegistrationDto> response = new Response<LegalEntityRegistrationDto>();
        verifyLegalEntity(legalEntityRegistrationDto, result);
        Employee employee = convertLegalEntityIntoEmployee(legalEntityRegistrationDto, result);
        Company company = convertLegalEntityIntoCompany(legalEntityRegistrationDto);

        try {
            if(result.hasErrors()){
                log.error("Error when validating registration data {}", result.getAllErrors());
                result.getAllErrors().forEach(objectError -> response.getErrors().add(objectError.getDefaultMessage()));
                return ResponseEntity.badRequest().body(response);
            }
            this.employeeService.insert(employee);
            this.companyService.insert(company);
        }catch (Exception e){
            log.error("Error when inserting on DataBase", e);
        }
        this.companyService.insert(company);
        employee.setCompany(company);
        this.employeeService.insert(employee);
        response.setData(convertEmployeeToLegalEntityDto(employee));
        return ResponseEntity.ok(response);
    }

    public Employee convertLegalEntityIntoEmployee(LegalEntityRegistrationDto legalEntityRegistrationDto, BindingResult result)
            throws NoSuchAlgorithmException{
        Employee employee = new Employee();
        employee.setNome(legalEntityRegistrationDto.getName());
        employee.setEmail(legalEntityRegistrationDto.getEmail());
        employee.setCpf(legalEntityRegistrationDto.getCpf());
        employee.setPerfil(PerfilEnum.ROLE_ADMIN);
        employee.setSenha(PasswordUtils.generatesBCrypt(legalEntityRegistrationDto.getPassword()));

        return employee;
    }

    public Company convertLegalEntityIntoCompany(LegalEntityRegistrationDto legalEntityRegistrationDto){
        Company company = new Company();
        company.setCnpj(legalEntityRegistrationDto.getCnpj());
        company.setRazaoSocial(legalEntityRegistrationDto.getSocialReason());

        return company;
    }

    public void verifyLegalEntity(LegalEntityRegistrationDto legalEntityRegistrationDto, BindingResult result){
        if(this.companyService.findByCnpj(legalEntityRegistrationDto.getCnpj()).isPresent()){
            result.addError(new ObjectError("company", "Company already exists"));
        }
        if(this.employeeService.findEmployeeByCpf(legalEntityRegistrationDto.getCpf()).isPresent()){
            result.addError(new ObjectError("employee", "CPF already exists"));
        }
        if(this.employeeService.findEmplyeeByEmail(legalEntityRegistrationDto.getEmail()).isPresent()){
            result.addError(new ObjectError("employee", "Email already exists"));
        }
    }

    public LegalEntityRegistrationDto convertEmployeeToLegalEntityDto(Employee employee){
        LegalEntityRegistrationDto legalEntity = new LegalEntityRegistrationDto();
        legalEntity.setId(employee.getId());
        legalEntity.setCpf(employee.getCpf());
        legalEntity.setEmail(employee.getEmail());
        legalEntity.setName(employee.getNome());
        legalEntity.setCnpj(employee.getCompany().getCnpj());
        legalEntity.setSocialReason(employee.getCompany().getRazaoSocial());

        return legalEntity;
    }

}
