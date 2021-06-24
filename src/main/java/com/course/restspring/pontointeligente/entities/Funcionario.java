package com.course.restspring.pontointeligente.entities;

import com.course.restspring.pontointeligente.enums.PerfilEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "funcionario")
public class Funcionario {

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
    private Empresa empresa;
    private List<Lancamento> lancamentos;

    public Funcionario(){
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

    @Column(name = "valor_hora", nullable = false)
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

    @Column(name = "horas_trabalhadas_dia", nullable = false)
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

    @Column(name = "horas_almoco", nullable = false)
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
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
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
        return "Funcionario [id=" + id +" nome=" + nome + ", email= " + email + ", cpf=" + cpf +
                ", empresa=" + empresa + ", valorHora" + valorHora + "perfil=" + perfil +
                ", horasTrabalhadasDia= " + horasTrabalhadasDia + ", horasAlmoco=" + horasAlmoco +
                ", dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + "]";
    }
}

