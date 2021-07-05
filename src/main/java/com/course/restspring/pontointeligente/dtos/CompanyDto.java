package com.course.restspring.pontointeligente.dtos;


public class CompanyDto {

    private Long id;
    private String socialReason;
    private String cnpj;

    public CompanyDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocialReason() {
        return socialReason;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString(){
        return "Company:[id" + id + ", socialReason:" + socialReason + ", cnpj:" + cnpj + " ]";
    }

}
