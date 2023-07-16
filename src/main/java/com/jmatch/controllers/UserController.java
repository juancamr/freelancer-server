package com.jmatch.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jmatch.models.Response;
import com.jmatch.models.User;
import com.jmatch.requestModel.LoginRequest;
import com.jmatch.requestModel.RegisterRequest;
import com.jmatch.services.UserService;
import com.jmatch.utils.Const;
import com.jmatch.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")

public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ObjectNode> login(@RequestBody LoginRequest loginRequest) {
        if (Utils.checkRequestParams(loginRequest)) {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            Response<User> res = userService.loginService(username, password);
            if (res.isSuccess())
                return ResponseEntity.ok(json()
                        .put("success", true)
                        .putPOJO("user", res.getData()));
            else
                return ResponseEntity.ok(json()
                        .put("success", false).
                        put("error", res.getError()));
        } else
            return insuficientParams();
    }

    @PostMapping("/register")
    public ResponseEntity<ObjectNode> register(@RequestBody RegisterRequest registerRequest) {
        if (Utils.checkRequestParams(registerRequest)) {
            if (Utils.isValidEmail(registerRequest.getCorreo())) {
                Response<User> res = userService.registerService(registerRequest);
                if (res.isSuccess())
                    return ResponseEntity.ok(json()
                            .put("success", true)
                            .putPOJO("user", res.getData()));
                else
                    return ResponseEntity.ok(json()
                            .put("success", false)
                            .put("error", res.getError()));

            } else
                return ResponseEntity.ok(json()
                        .put("success", false)
                        .put("error", Const.Errors.INVALID_EMAIL));
        } else {
            return insuficientParams();
        }
    }
}
