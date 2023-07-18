package com.jmatch.services;

import com.jmatch.interfaces.ProjectRelated;
import com.jmatch.models.Project;
import com.jmatch.models.Response;
import com.jmatch.repositories.ProjectRepository;
import com.jmatch.requestModel.ProjectRegisterRequest;
import com.jmatch.requestModel.UpdateProjectRequest;
import com.jmatch.utils.Const;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends BaseService<Project> {

  @Autowired private ProjectRepository projectRepository;

  public List<Project> getProjects() {
    return projectRepository.findAll();
  }

  public Response<Project> getProjectById(int id) {

    Optional<Project> f = projectRepository.findById(id);

    if (f.isEmpty()) {
      return res(false, null);
    }

    Project project = f.get();
    return res(true, project);
  }

  public Response<Project> addProjectService(ProjectRegisterRequest data) {

    ProjectRelated savedProject =
        projectRepository.save(
            new Project.Builder().projectname(data.getName()).image(data.getImage()).build());

    if (savedProject.getId() == 0) {
      return res(false, Const.Errors.SOMETHING_WENT_WRONG);
    }

    return res(true, savedProject);
  }

  public Response<Project> updateProjectService(long id, UpdateProjectRequest request) {

    Optional<Project> f = projectRepository.findById(id);

    if (f.isEmpty()) {
      return res(false, null);
    }

    Project project = f.get();
    project.setImage(request.getImage());
    project.setProjectname(request.getName());

    ProjectRelated savedProject = projectRepository.save(project);

    return res(true, savedProject);
  }

  public Boolean deleteProjectService(long id) {

    Optional<Project> f = projectRepository.findById(id);

    if (f.isEmpty()) {
      return false;
    }

    Project project = f.get();

    projectRepository.delete(project);

    return true;
  }
}
