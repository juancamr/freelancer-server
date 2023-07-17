package com.jmatch.controllers;

import com.jmatch.models.Project;
import com.jmatch.models.Response;
import com.jmatch.requestModel.RegisterRequest;
import com.jmatch.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/project")

public class ProjectController extends BaseController {
    @Autowired private ProjectService projectService;

    @GetMapping("/projects")
    public ResponseEntity<ArrayList<Project>> getAllProjects() {
        return  ResponseEntity.ok(projectService.fillAllProjects());
    }
}
