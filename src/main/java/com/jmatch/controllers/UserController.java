package com.jmatch.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jmatch.models.Freelancer;
import com.jmatch.models.Response;
import com.jmatch.models.User;
import com.jmatch.requestModel.LoginRequest;
import com.jmatch.requestModel.RegisterRequest;
import com.jmatch.requestModel.UpdateProfileRequest;
import com.jmatch.services.FreelancerService;
import com.jmatch.services.UserService;
import com.jmatch.utils.Const;
import com.jmatch.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")

public class UserController extends BaseController {

    private final UserService userService;
    private final FreelancerService freelancerService;

    @Autowired
    public UserController(UserService userService, FreelancerService freelancerService) {
        this.userService = userService;
        this.freelancerService = freelancerService;
    }

    @PostMapping("/register")
    public ResponseEntity<ObjectNode> register(@RequestBody RegisterRequest registerRequest) {
        if (Utils.checkRequestParams(registerRequest)) {
            if (Utils.isValidEmail(registerRequest.getCorreo())) {
                Response<User> res = userService.registerService(registerRequest);
                if (res.isSuccess()) return created(successJson().putPOJO("user", res.getData()));
                else return unprocessable(res.getError());
            } else return badRequest(Const.Errors.INVALID_EMAIL);
        } else return insuficientParams();
    }

    @PostMapping("/login")
    public ResponseEntity<ObjectNode> login(@RequestBody LoginRequest loginRequest) {
        if (Utils.checkRequestParams(loginRequest)) {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            Response<User> res = userService.loginService(username, password);
            if (res.isSuccess()) return res(successJson().putPOJO("user", res.getData()));
            else return unprocessable(res.getError());
        } else return insuficientParams();
    }

    @GetMapping("/freelancers")
    public ResponseEntity<?> getAllFreelancers() {
        Response<Freelancer> res = freelancerService.getAllForUsers();
        return res(successJson().putPOJO("freelancer_list", res.getDataList()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        if (Utils.checkRequestParams(updateProfileRequest)) {
            Response<User> res = userService.updateFreelancerProfile(updateProfileRequest);
            if (res.isSuccess()) return res(successJson());
            else return unprocessable(res.getError());
        } else return insuficientParams();
    }

    @PostMapping("/is_their_password")
    public ResponseEntity<?> isTheirPassword(@RequestBody Map<String, String> requestBody) {
        String password = requestBody.get("password");
        int id = Integer.parseInt(requestBody.get("id"));
        if (password.length() < 10 && id != 0) return badRequest("Contrasenia invalida, minimo 10 caracteres");
        else return res(json().put("success", userService.isTheirPassword(password, id)));
    }

    @PatchMapping("/change_password")
    public ResponseEntity<?> changePassword(@RequestBody Map<Object, String> requestBody) {
        String password = requestBody.get("password");
        int id = Integer.parseInt(requestBody.get("id"));
        if (password.length() < 10 && id != 0) return badRequest("Contrasenia invalida, minimo 10 caracteres");
        else {
            Response<User> res = userService.changePassword(password, id);
            if (res.isSuccess()) return res(successJson());
            else return unprocessable(res.getError());
        }
    }
}
