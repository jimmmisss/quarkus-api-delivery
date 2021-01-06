package com.github.jimmmisss.rango.cadastro;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "Prato")
public class Prato extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nome;
    public String descricao;
    public Double preco;

    @ManyToOne
    public Restaurante restaurante;

}
