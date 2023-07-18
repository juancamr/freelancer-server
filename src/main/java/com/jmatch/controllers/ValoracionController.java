package com.jmatch.controllers;

import com.jmatch.models.Response;
import com.jmatch.models.Valoracion;
import com.jmatch.requestModel.CrearValoracionRequest;
import com.jmatch.services.ValoracionService;
import com.jmatch.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/valoracion")
public class ValoracionController extends BaseController {

    ValoracionService valoracionService;

    public ValoracionController(ValoracionService valoracionService) {
        this.valoracionService = valoracionService;
    }

    @PostMapping("/create_valoracion")
    ResponseEntity<?> crearValoracion(@RequestBody CrearValoracionRequest crearValoracionRequest) {
        if (Utils.checkRequestParams(crearValoracionRequest)) {
            Response<Valoracion> res = valoracionService.crearValoracion(crearValoracionRequest);
            if (res.isSuccess()) return created(successJson().putPOJO("valoracion", res.getData()));
             else return unprocessable(res.getError());
        } else return insuficientParams();
    }

    @GetMapping("/get_all")
    ResponseEntity<?> getAllValoracion(@RequestBody Map<String, Integer> request) {
        int idFreelancer = request.get("id");
        Response<Valoracion> res = valoracionService.obtenerValoraciones(idFreelancer);
        if (res.isSuccess()) return res(successJson().putPOJO("valoracion_list", res.getDataList()));
        else return unprocessable(res.getError());
    }

}
