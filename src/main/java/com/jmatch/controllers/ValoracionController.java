package com.jmatch.controllers;

import com.jmatch.models.Freelancer;
import com.jmatch.models.Response;
import com.jmatch.models.User;
import com.jmatch.models.Valoracion;
import com.jmatch.requestModel.CrearValoracionRequest;
import com.jmatch.services.FreelancerService;
import com.jmatch.services.UserService;
import com.jmatch.services.ValoracionService;
import com.jmatch.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/valoracion")
public class ValoracionController extends BaseController {

    ValoracionService valoracionService;
    FreelancerService freelancerService;
    UserService userService;

    public ValoracionController(ValoracionService valoracionService, FreelancerService freelancerService, UserService userService) {
        this.valoracionService = valoracionService;
        this.freelancerService = freelancerService;
        this.userService = userService;
    }

    @PostMapping("/create")
    ResponseEntity<?> crearValoracion(@RequestBody CrearValoracionRequest crearValoracionRequest) {
        if (Utils.checkRequestParams(crearValoracionRequest)) {
            Response<Freelancer> resFreelancer = freelancerService.getFreelancer(crearValoracionRequest.getIdFreelancer());
            Response<User> resUsuario = userService.getUserService(crearValoracionRequest.getIdUsuario());
            if (resFreelancer.isSuccess()) {
                if (resUsuario.isSuccess()) {
                    Response<Valoracion> res = valoracionService.crearValoracion(crearValoracionRequest, resFreelancer.getData(), resUsuario.getData());
                    if (res.isSuccess()) return created(successJson().putPOJO("valoracion", res.getData()));
                    else return unprocessable(res.getError());
                } else return unprocessable(resUsuario.getError());
            } else return unprocessable(resFreelancer.getError());
        } else return insuficientParams();
    }

    @GetMapping("/get_all/{id}")
    ResponseEntity<?> getAllValoracion(@PathVariable int id) {
        if (id >= 0) {
            Response<Valoracion> res = valoracionService.obtenerValoraciones(id);
            if (res.isSuccess()) return res(successJson().putPOJO("valoracion_list", res.getDataList()));
            else return unprocessable(res.getError());
        } else return insuficientParams();
    }

}
