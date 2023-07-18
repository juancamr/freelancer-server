package com.jmatch.models;

import jakarta.persistence.*;

@Entity
@Table(name = "valoracion")
public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "freelancer")
    private Freelancer freelancer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario")
    private User usuario;
    private int rateStars;
    private String comentario;

    private Valoracion(Builder builder) {
        this.id = builder.id;
        this.freelancer = builder.freelancer;
        this.usuario = builder.usuario;
        this.rateStars = builder.rateStars;
        this.comentario = builder.comentario;
    }

    public int getId() {
        return id;
    }

    public Valoracion setId(int id) {
        this.id = id;
        return this;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public Valoracion setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
        return this;
    }

    public User getUsuario() {
        return usuario;
    }

    public Valoracion setUsuario(User usuario) {
        this.usuario = usuario;
        return this;
    }

    public int getRateStars() {
        return rateStars;
    }

    public Valoracion setRateStars(int rateStars) {
        this.rateStars = rateStars;
        return this;
    }

    public String getComentario() {
        return comentario;
    }

    public Valoracion setComentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public Valoracion() {
    }
    // Getters de los atributos

    public static class Builder {
        private int id;
        private Freelancer freelancer;
        private User usuario;
        private int rateStars;
        private String comentario;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder freelancer(Freelancer freelancer) {
            this.freelancer = freelancer;
            return this;
        }

        public Builder usuario(User usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder rateStars(int rateStars) {
            this.rateStars = rateStars;
            return this;
        }

        public Builder comentario(String comentario) {
            this.comentario = comentario;
            return this;
        }

        public Valoracion build() {
            return new Valoracion(this);
        }
    }

}
