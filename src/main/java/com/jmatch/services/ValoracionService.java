package com.jmatch.services;

import com.jmatch.models.Response;
import com.jmatch.models.Valoracion;
import com.jmatch.repositories.ValoracionRepository;
import com.jmatch.requestModel.CrearValoracionRequest;
import com.jmatch.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValoracionService extends BaseService<Valoracion> {

    ValoracionRepository valoracionRepository;

    @Autowired
    public ValoracionService(ValoracionRepository valoracionRepository) {
        this.valoracionRepository = valoracionRepository;
    }

    public Response<Valoracion> crearValoracion(CrearValoracionRequest crearValoracionRequest) {
        Valoracion savedValoracion = valoracionRepository.save(new Valoracion.Builder()
                .freelancer(crearValoracionRequest.getFreelancer())
                .usuario(crearValoracionRequest.getUsuario())
                .comentario(crearValoracionRequest.getComentario())
                .rateStars(crearValoracionRequest.getRateStars())
                .build()
        );
        if (savedValoracion.getComentario().isEmpty()) {
            return res(false, Const.Errors.SOMETHING_WENT_WRONG);
        } else return res(true, savedValoracion);
    }

    public Response<Valoracion> obtenerValoraciones(int idFreelancer) {
        List<Valoracion> listaValoraciones = valoracionRepository.obtenerValoracionesByFreelancer(idFreelancer);
        return res(true, listaValoraciones);
    }
}
