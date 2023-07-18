package com.jmatch.models;

import com.jmatch.interfaces.ProjectRelated;
import jakarta.persistence.*;

@Entity
@Table(name = "proyecto")
public class Project implements ProjectRelated {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="portfolio")
  private Portfolio portfolio;
  private String projectname;
  protected String image;

  public Project() {}

  private Project(Builder builder) {
    this.id = builder.id;
    this.projectname = builder.projectname;
    this.image = builder.image;
  }

  public static class Builder {
    private int id;
    private String projectname;
    private String image;

    public Builder() {
      // Inicialización de valores predeterminados si es necesario
    }

    public Builder id(int id) {
      this.id = id;
      return this;
    }

    public Builder projectname(String projectname) {
      this.projectname = projectname;
      return this;
    }

    public Builder image(String image) {
      this.image = image;
      return this;
    }

    public Project build() {
      return new Project(this);
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getProjectname() {
    return projectname;
  }

  @Override
  public String getImage() {
    return image;
  }

  @Override
  public void setProjectname(String projectname) {
    this.projectname = projectname;
  }

  @Override
  public void setImage(String image) {
    this.image = image;
  }
}
