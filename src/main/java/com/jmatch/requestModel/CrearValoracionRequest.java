package com.jmatch.requestModel;

import com.jmatch.models.Freelancer;
import com.jmatch.models.User;

public class CrearValoracionRequest {
    int idFreelancer;
    int idUsuario;

    int rateStars;
    String comentario;

    public int getIdFreelancer() {
        return idFreelancer;
    }

    public void setIdFreelancer(int idFreelancer) {
        this.idFreelancer = idFreelancer;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getRateStars() {
        return rateStars;
    }

    public void setRateStars(int rateStars) {
        this.rateStars = rateStars;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
