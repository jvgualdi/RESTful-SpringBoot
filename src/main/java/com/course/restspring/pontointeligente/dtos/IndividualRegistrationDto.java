package com.course.restspring.pontointeligente.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

public class IndividualRegistrationDto {

    private Long id;
    private String name;
    private String password;
    private String cpf;
    private String email;
    private Optional<String> valorHora = Optional.empty();
    private Optional<String> horasTrabalhadasDia = Optional.empty();
    private Optional<String> horasAlmoco = Optional.empty();
    private String cnpj;

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

    @NotEmpty(message = "Password can not be empty")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "CPF can not be empty")
    @CPF(message = "Invalid CPF format")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    @NotEmpty(message = "CNPJ can not be empty")
    @CNPJ(message = "Invalid CNPJ format")
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
