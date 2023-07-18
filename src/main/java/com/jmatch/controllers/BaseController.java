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
        return res(unsuccessJson().put("error", Const.Errors.INSUFICIENT_PARAMS));
    }

    public ObjectNode successJson() {
        return json().put("success", true);
    }

    public ObjectNode unsuccessJson() {
        return json().put("success", false);
    }

    public ResponseEntity<ObjectNode> res(ObjectNode object) {
        return ResponseEntity.ok().body(object);
    }

    public ResponseEntity<ObjectNode> res(int status, ObjectNode object) {
        return ResponseEntity.status(status).body(object);
    }

    public ResponseEntity<ObjectNode> badRequest(String error) {
        return res(400, json().put("success", false).put("error", error));
    }

    public ResponseEntity<ObjectNode> created(ObjectNode object) {
        return ResponseEntity.status(201).body(object);
    }

    public ResponseEntity<ObjectNode> unprocessable(String error) {
        return res(422, json().put("success", false).put("error", error));
    }

}
