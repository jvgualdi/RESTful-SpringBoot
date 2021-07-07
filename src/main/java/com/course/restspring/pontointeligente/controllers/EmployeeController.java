package com.course.restspring.pontointeligente.controllers;

import com.course.restspring.pontointeligente.dtos.EmployeeDto;
import com.course.restspring.pontointeligente.entities.Employee;
import com.course.restspring.pontointeligente.response.Response;
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
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController() {
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<EmployeeDto>> updateEmployee(@PathVariable("id") Long id, @Valid @RequestBody EmployeeDto employeeDto, BindingResult result) throws NoSuchAlgorithmException {
        log.info("Updating employee data: {}", employeeDto.toString());
        Response<EmployeeDto> response = new Response<EmployeeDto>();
        Optional<Employee> employee = this.employeeService.findEmployeeById(id);

        if (!employee.isPresent()){
            result.addError(new ObjectError("employee", "Employee not found"));
        }

        this.updateEmployeeData(employee.get(), employeeDto, result);

        if (result.hasErrors()){
            log.error("Error when validating employee: {}", result.getAllErrors());
            result.getAllErrors().forEach(objectError -> response.getErrors().add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.employeeService.insert(employee.get());
        response.setData(this.mapEmployeeDto(employee.get()));

        return ResponseEntity.ok(response);
    }



    private void updateEmployeeData(Employee employee, EmployeeDto employeeDto, BindingResult result) throws NoSuchAlgorithmException {
        employee.setNome(employeeDto.getName());

        if (!employee.getEmail().equals(employeeDto.getEmail())){
            if(this.employeeService.findEmplyeeByEmail(employeeDto.getEmail()).isPresent())
                result.addError(new ObjectError("email", "E-mail already exists"));
        }

        if (employeeDto.getHorasAlmoco().isPresent()) {
            employee.setHorasAlmoco(Float.valueOf(employeeDto.getHorasAlmoco().get()));
        }

        if (employeeDto.getHorasTrabalhadasDia().isPresent()){
            employee.setHorasTrabalhadasDia(Float.valueOf(employeeDto.getHorasTrabalhadasDia().get()));
        }

        if (employeeDto.getValorHora().isPresent()){
            employee.setValorHora(new BigDecimal(employeeDto.getValorHora().get()));
        }

        if (employeeDto.getPassword().isPresent()){
            employee.setSenha(PasswordUtils.generatesBCrypt(employeeDto.getPassword().get()));
        }
    }

    private EmployeeDto mapEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getNome());
        employeeDto.setEmail(employee.getEmail());
        if (employee.getHorasAlmocoOpt().isPresent()) employeeDto.setHorasAlmoco(Optional.of(Float.toString(employee.getHorasAlmoco())));
        if (employee.getHorasTrabalhadasDiaOpt().isPresent()) employeeDto.setHorasTrabalhadasDia(Optional.of(Float.toString(employee.getHorasTrabalhadasDia())));
        if (employee.getValorHoraOpt().isPresent()) employeeDto.setValorHora(Optional.of(employee.getValorHora().toString()));

        return employeeDto;
    }

}
