package com.course.restspring.pontointeligente.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class EmployeeDto {

    private Long id;
    private String name;
    private String email;
    private Optional<String> password = Optional.empty();
    private Optional<String> valorHora = Optional.empty();
    private Optional<String> horasTrabalhadasDia = Optional.empty();
    private Optional<String> horasAlmoco = Optional.empty();


    public EmployeeDto() {
    }

    @NotEmpty(message = "Name can not be empty")
    @Length(min = 3, max = 200, message = "Name must have between 3 and 200 characters")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "Name can not be empty")
    @Length(min = 3, max = 200, message = "Name must have between 3 and 200 characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "E-mail can not be empty")
    @Email(message = "Invalid e-mail format")
    @Length(min = 5, max = 200, message = "E-mail must have between 3 and 200 characters")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public void setPassword(Optional<String> password) {
        this.password = password;
    }

    public Optional<String> getValorHora() {
        return valorHora;
    }

    public void setValorHora(Optional<String> valorHora) {
        this.valorHora = valorHora;
    }

    public Optional<String> getHorasTrabalhadasDia() {
        return horasTrabalhadasDia;
    }

    public void setHorasTrabalhadasDia(Optional<String> horasTrabalhadasDia) {
        this.horasTrabalhadasDia = horasTrabalhadasDia;
    }

    public Optional<String> getHorasAlmoco() {
        return horasAlmoco;
    }

    public void setHorasAlmoco(Optional<String> horasAlmoco) {
        this.horasAlmoco = horasAlmoco;
    }

    @Override
    public String toString(){
        return "Employee [id:" + id + ", name:" + name + ", e-mail:" + email + ", password:" + password +
                ", valorHora=" + valorHora + ", horasTrabalhadasDia=" + horasTrabalhadasDia +
                ", horasAlmoço=" + horasAlmoco + " ]";
    }

}
