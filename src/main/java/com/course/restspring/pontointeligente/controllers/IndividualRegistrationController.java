package com.course.restspring.pontointeligente.controllers;

import com.course.restspring.pontointeligente.dtos.IndividualRegistrationDto;
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class IndividualRegistrationController {

    private static final Logger log = LoggerFactory.getLogger(IndividualRegistrationController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    public IndividualRegistrationController(){
    }

    @PostMapping(value = "individual-registration")
    public ResponseEntity<Response<IndividualRegistrationDto>> individualRegistration(@Valid @RequestBody IndividualRegistrationDto individualRegistrationDto,
                                                                                      BindingResult result){
        log.info("Registring individual {}", individualRegistrationDto.toString());
        Response<IndividualRegistrationDto> response = new Response<IndividualRegistrationDto>();
        validatesIndividualRegistration(individualRegistrationDto, result);
        try{
            if (result.hasErrors()) {
                log.error("Error when validating registration data {}", result.getAllErrors());
                result.getAllErrors().forEach(objectError -> response.getErrors().add(objectError.getDefaultMessage()));
                return ResponseEntity.badRequest().body(response);
            }
            Employee employee = mapIndividualDtoToEmployee(individualRegistrationDto);
            employee.setCompany(companyService.findByCnpj(individualRegistrationDto.getCnpj()).get());
            this.employeeService.insert(employee);
            response.setData(this.mapEmployeeToIndividualDto(employee));
            return ResponseEntity.ok(response);
        }catch (Exception e){
            log.error("Error when mapping and inserting Employee on Database");
            log.error(e.toString());
            return ResponseEntity.badRequest().body(response);
        }
    }

    public void validatesIndividualRegistration (IndividualRegistrationDto individualDto, BindingResult result){
        if (!companyService.findByCnpj(individualDto.getCnpj()).isPresent()){
            result.addError(new ObjectError("company", "Company does not exist"));
        }
        if (employeeService.findEmployeeByCpf(individualDto.getCpf()).isPresent()){
            result.addError(new ObjectError("employee", "CPF already registered"));
        }
        if(employeeService.findEmplyeeByEmail(individualDto.getEmail()).isPresent()){
            result.addError(new ObjectError("employee", "E-mail already registered"));
        }
    }

    public Employee mapIndividualDtoToEmployee(IndividualRegistrationDto individual) throws NoSuchAlgorithmException{
        Employee employee = new Employee();
        employee.setNome(individual.getName());
        employee.setEmail(individual.getEmail());
        employee.setCpf(individual.getCpf());
        employee.setPerfil(PerfilEnum.ROLE_USUARIO);
        employee.setSenha(PasswordUtils.generatesBCrypt(individual.getPassword()));
        if(individual.getHorasAlmoco().isPresent()) employee.setHorasAlmoco(Float.valueOf(individual.getHorasAlmoco().get()));
        if(individual.getValorHora().isPresent()) employee.setValorHora(new BigDecimal(individual.getValorHora().get()));
        if(individual.getHorasTrabalhadasDia().isPresent()) employee.setHorasTrabalhadasDia(Float.valueOf(individual.getHorasTrabalhadasDia().get()));

        return employee;
    }

    public IndividualRegistrationDto mapEmployeeToIndividualDto(Employee employee){
        IndividualRegistrationDto individualDto = new IndividualRegistrationDto();
        individualDto.setId(employee.getId());
        individualDto.setCnpj(employee.getCompany().getCnpj());
        individualDto.setCpf(employee.getCpf());
        individualDto.setEmail(employee.getEmail());
        if (employee.getHorasAlmocoOpt().isPresent()) individualDto.setHorasAlmoco(Optional.of(Float.toString(employee.getHorasAlmoco())));
        if (employee.getHorasTrabalhadasDiaOpt().isPresent()) individualDto.setHorasTrabalhadasDia(Optional.of(Float.toString(employee.getHorasTrabalhadasDia())));
        if (employee.getValorHoraOpt().isPresent()) individualDto.setValorHora(Optional.of(employee.getValorHora().toString()));

        return individualDto;
    }


}

