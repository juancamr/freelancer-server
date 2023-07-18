package com.jmatch.requestModel;

import com.jmatch.models.Freelancer;
import com.jmatch.models.User;

public class CrearValoracionRequest {
    Freelancer freelancer;
    User usuario;

    int rateStars;
    String comentario;

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public CrearValoracionRequest setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
        return this;
    }

    public User getUsuario() {
        return usuario;
    }

    public CrearValoracionRequest setUsuario(User usuario) {
        this.usuario = usuario;
        return this;
    }

    public int getRateStars() {
        return rateStars;
    }

    public CrearValoracionRequest setRateStars(int rateStars) {
        this.rateStars = rateStars;
        return this;
    }

    public String getComentario() {
        return comentario;
    }

    public CrearValoracionRequest setComentario(String comentario) {
        this.comentario = comentario;
        return this;
    }
}
