package com.course.restspring.pontointeligente.entities;

import com.course.restspring.pontointeligente.enums.PerfilEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private BigDecimal valorHora;
    private Float horasTrabalhadasDia;
    private Float horasAlmoco;
    private PerfilEnum perfil;
    private Date dataCriacao;
    private Date dataAtualizacao;
    private Company company;
    private List<Release> releases;

    public Employee(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "nome", nullable = false)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "senha", nullable = false)
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Column(name = "cpf", nullable = false)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Column(name = "valor_hora", nullable = true)
    public BigDecimal getValorHora() {
        return valorHora;
    }

    @Transient
    public Optional<BigDecimal> getValorHoraOpt(){
        return Optional.ofNullable(valorHora);
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    @Column(name = "horas_trabalhadas_dia", nullable = true)
    public Float getHorasTrabalhadasDia() {
        return horasTrabalhadasDia;
    }

    @Transient
    public Optional<Float> getHorasTrabalhadasDiaOpt(){
        return Optional.ofNullable(horasTrabalhadasDia);
    }

    public void setHorasTrabalhadasDia(Float horasTrabalhadasDia) {
        this.horasTrabalhadasDia = horasTrabalhadasDia;
    }

    @Column(name = "horas_almoco", nullable = true)
    public Float getHorasAlmoco() {
        return horasAlmoco;
    }

    @Transient
    public Optional<Float> getHorasAlmocoOpt(){
        return Optional.ofNullable(horasAlmoco);
    }

    public void setHorasAlmoco(Float horasAlmoco) {
        this.horasAlmoco = horasAlmoco;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    public PerfilEnum getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }

    @Column(name = "data_criacao", nullable = false)
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Column(name = "data_atualizacao", nullable = false)
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Release> getReleases() {
        return releases;
    }

    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }

    @PreUpdate
    public void preUpdate(){
        dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist(){
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }

    @Override
    public String toString(){
        return "Employee [id=" + id +" nome=" + nome + ", email= " + email + ", cpf=" + cpf +
                ", company=" + company + ", valorHora" + valorHora + "perfil=" + perfil +
                ", horasTrabalhadasDia= " + horasTrabalhadasDia + ", horasAlmoco=" + horasAlmoco +
                ", dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + "]";
    }
}

