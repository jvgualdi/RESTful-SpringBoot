package com.course.restspring.pontointeligente.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LegalEntityRegistrationDto {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private String cnpj;
    private String socialReason;

    public LegalEntityRegistrationDto(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "Name can no be empty.")
    @Length(min = 3, max = 200, message = "Name must have between 3 and 200 characters.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "CPF can not be empty.")
    @CPF(message = "Invalid CPF")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @NotEmpty(message = "Email can not be empty.")
    @Length(min = 5, max = 200, message = "Email must have between 5 and 200 characters")
    @Email(message = "Invalid email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Password can not be empty.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    @NotEmpty(message = "CNPJ can not be empty")
    @CNPJ(message = "Invalid CNPJ")
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @NotEmpty(message = "Social Reason can not be empty.")
    @Length(min = 5, max = 200, message = "Social Reason must have between 5 and 200 characters")
    public String getSocialReason() {
        return socialReason;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    @Override
    public String toString(){
        return "LegalEntityRegistrationDto [id=" + id + ", name=" + name + ", email=" + email +
                ", password=" + password + ", cpf=" + cpf +", socialReason=" + socialReason +
                ", cnpj=" + cnpj +"]";
    }
}
