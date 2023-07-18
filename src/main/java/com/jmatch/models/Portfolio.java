package com.jmatch.models;

import jakarta.persistence.*;

@Entity
@Table(name="portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="portfolio")
    private Portfolio portfolio;

    private String descripcion;

    public Portfolio(Portfolio portfolio, String descripcion) {
        this.descripcion = descripcion;
        this.portfolio = portfolio;
    }
    public Portfolio() {}

    public int getId() {
        return id;
    }

    public Portfolio setId(int id) {
        this.id = id;
        return this;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public Portfolio setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
        return this;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Portfolio setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }
}
