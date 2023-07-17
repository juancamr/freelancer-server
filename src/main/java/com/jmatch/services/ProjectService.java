package com.jmatch.services;

import com.jmatch.models.Project;
import com.jmatch.models.Response;
import com.jmatch.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProjectService extends BaseService<Project> {

    //@Autowired private ProjectRepository projectRepository;

    public ArrayList<Project> fillAllProjects() {
        ArrayList<Project> projects = new ArrayList<Project>();
        for (int i=0; i<10; i++){
            Project p = new Project.Builder().id(i).image("cdcds").projectname("cds").build();
            projects.add(p);
        }
        return projects;
    }
}
