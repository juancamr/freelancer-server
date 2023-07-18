package com.jmatch.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jmatch.models.Project;
import com.jmatch.models.Response;
import com.jmatch.requestModel.ProjectRegisterRequest;
import com.jmatch.requestModel.UpdateProjectRequest;
import com.jmatch.services.ProjectService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController extends BaseController {
  @Autowired private ProjectService projectService;

  Logger logger = LoggerFactory.getLogger(getClass());

  @GetMapping("/")
  public ResponseEntity<List<Project>> getAllProjects() {
    return ResponseEntity.ok(projectService.getProjects());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ObjectNode> getProjectById(@PathVariable int id) {
    Response<Project> res = projectService.getProjectById(id);

    return ResponseEntity.ok(json().put("success", true).putPOJO("project", res.getData()));
  }

  @PostMapping("/")
  public ResponseEntity<ObjectNode> addProject(@RequestBody ProjectRegisterRequest request) {
    Response<Project> res = projectService.addProjectService(request);
    logger.info(request.toString());

    return ResponseEntity.ok(json().put("success", true).putPOJO("project", res.getData()));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ObjectNode> updateProject(
      @PathVariable int id, @RequestBody UpdateProjectRequest request) {
    Response<Project> res = projectService.updateProjectService((long) id, request);
    logger.info(request.toString());

    return ResponseEntity.ok(json().put("success", true).putPOJO("project", res.getData()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ObjectNode> deleteProject(@PathVariable int id) {
    Boolean operation = projectService.deleteProjectService((long) id);
    if (operation) {
      return ResponseEntity.ok(json().put("success", true).putPOJO("message", "Deleted"));
    }

    return ResponseEntity.ok(json().put("success", false).putPOJO("message", "Error found"));
  }
}
