package com.jmatch.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jmatch.models.Freelancer;
import com.jmatch.models.Response;
import com.jmatch.requestModel.LoginRequest;
import com.jmatch.requestModel.RegisterRequest;
import com.jmatch.requestModel.UpdateProfileRequest;
import com.jmatch.services.FreelancerService;
import com.jmatch.utils.Const;
import com.jmatch.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/freelancer")
public class FreelancerController extends BaseController {

    private final FreelancerService freelancerService;

    @Autowired
    public FreelancerController(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;
    }

    @PostMapping("/register")
    public ResponseEntity<ObjectNode> register(@RequestBody RegisterRequest registerRequest) {
        if (Utils.checkRequestParams(registerRequest)) {
            if (Utils.isValidEmail(registerRequest.getCorreo())) {
                Response<Freelancer> res = freelancerService.registerService(registerRequest);
                if (res.isSuccess()) return created(successJson().putPOJO("freelancer", res.getData()));
                else return unprocessable(res.getError());
            } else return badRequest(Const.Errors.INVALID_EMAIL);
        } else return insuficientParams();
    }

    @PostMapping("/login")
    public ResponseEntity<ObjectNode> login(@RequestBody LoginRequest loginRequest) {
        if (Utils.checkRequestParams(loginRequest)) {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            Response<Freelancer> res = freelancerService.loginService(username, password);
            if (res.isSuccess()) return res(successJson().putPOJO("freelancer", res.getData()));
            else return unprocessable(res.getError());
        } else return insuficientParams();
    }

    @PatchMapping("/set_category")
    public ResponseEntity<?> assignCategory(@RequestBody Map<String, Integer> requestBody) {
        int idFreelancer = requestBody.get("id_freelancer");
        int idCategoria = requestBody.get("id_categoria");
        if (idFreelancer != 0 && idCategoria != 0) {
            Response<Freelancer> res = freelancerService.assignCategory(idFreelancer, idCategoria);
            if (res.isSuccess()) return res(successJson());
            else return unprocessable(res.getError());
        } else return insuficientParams();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        if (Utils.checkRequestParams(updateProfileRequest)) {
            Response<Freelancer> res = freelancerService.updateFreelancerProfile(updateProfileRequest);
            if (res.isSuccess()) return res(successJson());
            else return unprocessable(res.getError());
        } else return insuficientParams();
    }

    @PostMapping("/is_their_password")
    public ResponseEntity<?> isTheirPassword(@RequestBody Map<String, String> requestBody) {
        String password = requestBody.get("password");
        int id = Integer.parseInt(requestBody.get("id"));
        if (password.length() < 10 && id != 0) return badRequest("Contrasenia invalida, minimo 10 caracteres");
        else return res(json().put("success", freelancerService.isTheirPassword(password, id)));
    }

    @PatchMapping("/change_password")
    public ResponseEntity<?> changePassword(@RequestBody Map<Object, String> requestBody) {
        String password = requestBody.get("password");
        int id = Integer.parseInt(requestBody.get("id"));
        if (password.length() < 10 && id != 0) return badRequest("Contrasenia invalida, minimo 10 caracteres");
        else {
            Response<Freelancer> res = freelancerService.changePassword(password, id);
            if (res.isSuccess()) return res(successJson());
            else return unprocessable(res.getError());
        }
    }
}
