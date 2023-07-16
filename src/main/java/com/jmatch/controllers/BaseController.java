package com.jmatch.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jmatch.utils.Const;
import org.springframework.http.ResponseEntity;

public class BaseController {

    public ObjectNode json() {
        return new ObjectMapper().createObjectNode();
    }

    public ResponseEntity<ObjectNode> insuficientParams() {
        return ResponseEntity.ok(json()
                .put("success", false)
                .put("error", Const.Errors.INSUFICIENT_PARAMS)
        );
    }

}
