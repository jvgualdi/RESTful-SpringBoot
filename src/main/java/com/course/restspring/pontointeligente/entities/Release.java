package com.course.restspring.pontointeligente.entities;

import com.course.restspring.pontointeligente.enums.TipoEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "release")
public class Release {

    private Long id;
    private Date data;
    private String descricao;
    private String localizacao;
    private Date dataCriacao;
    private Date dataAtualizacao;
    private TipoEnum tipo;
    private Employee employee;

    public Release(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data", nullable = false)
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Column(name = "descricao", nullable = false)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name = "localizacao", nullable = false)
    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Column(name = "dataCriacao", nullable = false)
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Column(name = "dataAtualizacao", nullable = false)
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    public TipoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoEnum tipo) {
        this.tipo = tipo;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        return "Release [id=" + id + ", data=" + data + ", descricao=" + descricao +
                ", localizacao=" + localizacao + ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao + ", tipo=" + tipo +
                ", employee=" + employee + "]";
    }
}
