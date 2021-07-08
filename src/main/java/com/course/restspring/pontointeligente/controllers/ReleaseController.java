package com.course.restspring.pontointeligente.controllers;


import com.course.restspring.pontointeligente.dtos.ReleaseDto;
import com.course.restspring.pontointeligente.entities.Employee;
import com.course.restspring.pontointeligente.entities.Release;
import com.course.restspring.pontointeligente.enums.PerfilEnum;
import com.course.restspring.pontointeligente.enums.TipoEnum;
import com.course.restspring.pontointeligente.response.Response;
import com.course.restspring.pontointeligente.services.EmployeeService;
import com.course.restspring.pontointeligente.services.ReleaseService;
import com.course.restspring.pontointeligente.utils.PasswordUtils;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/releases")
public class ReleaseController {

    private static final Logger log = LoggerFactory.getLogger(ReleaseController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ReleaseService releaseService;

    @Autowired
    private EmployeeService employeeService;

    public ReleaseController() {
    }

    @GetMapping(value = "/employee/{employeeId}")
    public ResponseEntity<Response<List<ReleaseDto>>> listByEmployeeId (
            @PathVariable("employeeId") Long employeeId,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir){

        log.info("Searching for releases by employee ID: {}", employeeId);
        Response<List<ReleaseDto>> response = new Response<List<ReleaseDto>>();

        List<Release> releases = this.releaseService.findByEmployeeId(employeeId);
        List<ReleaseDto> releasesDto = new ArrayList<ReleaseDto>();
        for (Release release: releases) {
            releasesDto.add(this.mapReleaseDto(release));
        }

        response.setData(releasesDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<ReleaseDto>> listById(@PathVariable("id") Long id){
        log.info("Searching release by ID: {}", id);
        Response<ReleaseDto> response = new Response<ReleaseDto>();
        Optional<Release> release = this.releaseService.findById(id);

        if (!release.isPresent()){
            log.error("Release not found by ID: {}", id);
            response.getErrors().add("Release not found by ID: {}" + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(mapReleaseDto(release.get()));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Response<ReleaseDto>> insertRelease(@Valid @RequestBody ReleaseDto releaseDto,
                                                              BindingResult result){
        log.info("Inserting release: {}", releaseDto.toString());
        Response<ReleaseDto> response = new Response<ReleaseDto>();
        validatesEmployee(releaseDto, result);
        Release release = this.convertDtoToRelease(releaseDto, result);
        if (result.hasErrors()){
            log.error("Error when validating release: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        release = this.releaseService.insertOnDB(release);
        response.setData(this.mapReleaseDto(release));
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Response<ReleaseDto>> updateRelease(@PathVariable("id") Long id,
                                                              @Valid @RequestBody ReleaseDto releaseDto, BindingResult result){
        log.info("Started release update ID: {}", id);
        Response<ReleaseDto> response = new Response<ReleaseDto>();
        validatesEmployee(releaseDto, result);
        releaseDto.setId(Optional.of(id));
        Release release = this.convertDtoToRelease(releaseDto, result);

        if (result.hasErrors()){
            log.error("Error when validating release: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        release = this.releaseService.insertOnDB(release);
        response.setData(this.mapReleaseDto(release));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Response<String>> deleteRelease(@PathVariable("id") Long id){
        log.info("Deleting release: {}", id);
        Response<String> response = new Response<String>();
        Optional<Release> release = this.releaseService.findById(id);

        if (!release.isPresent()){
            log.error("Error when deleting release with ID: {} ", id);
            response.getErrors().add("Error when deleting release. No registration found for the ID: {}" + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.releaseService.deleteFromDB(id);
        return ResponseEntity.ok(new Response<String>());
    }

    protected void validatesEmployee(ReleaseDto releaseDto, BindingResult result){
        if (releaseDto.getEmployeeId() == null){
            result.addError(new ObjectError("employee", "employee does not exist"));
            return;
        }

        log.info("Validating employee id: {}", releaseDto.getEmployeeId());
        Optional<Employee> employee = this.employeeService.findEmployeeById(releaseDto.getEmployeeId());
        if (!employee.isPresent()){
            result.addError(new ObjectError("employee", "Employee not found. ID does not exist"));
        }
    }

    protected Release convertDtoToRelease(ReleaseDto releaseDto, BindingResult result){
        Release release = new Release();

        if (releaseDto.getId().isPresent()){
            Optional<Release> rel = this.releaseService.findById(releaseDto.getId().get());
            if (rel.isPresent()){
                release = rel.get();
            }else{
                result.addError(new ObjectError("release", "Release not found"));
            }
        }else{
            release.setEmployee(new Employee());
            release.getEmployee().setId(releaseDto.getEmployeeId());
        }
        try {
            release.setDescricao(releaseDto.getDescription());
            release.setLocalizacao(releaseDto.getLocalization());
            release.setData(this.dateFormat.parse(releaseDto.getDate()));
        }catch (Exception e){
            log.error("Error when parsing date");
            result.addError(new ObjectError("release", "Error when parsing release date"));
        }
        if (EnumUtils.isValidEnum(TipoEnum.class, releaseDto.getType())){
            release.setTipo(TipoEnum.valueOf(releaseDto.getType()));
        }else{
            result.addError(new ObjectError("type", "Invalid type"));
        }
        return release;
    }

    protected ReleaseDto mapReleaseDto(Release release){
        ReleaseDto releaseDto = new ReleaseDto();

        releaseDto.setId(Optional.of(release.getId()));
        releaseDto.setDate(this.dateFormat.format(release.getData()));
        releaseDto.setType(release.getTipo().toString());
        releaseDto.setDescription(release.getDescricao());
        releaseDto.setLocalization(release.getLocalizacao());
        releaseDto.setEmployeeId(release.getEmployee().getId());

        return releaseDto;
    }

}
