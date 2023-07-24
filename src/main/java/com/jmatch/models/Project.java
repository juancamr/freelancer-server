package com.jmatch.models;

import com.jmatch.interfaces.ProjectRelated;
import jakarta.persistence.*;

@Entity
@Table(name = "proyecto")
public class Project implements ProjectRelated {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_portfolio")
    private Portfolio portfolio;
    private String nombre;
    protected String image_path;

    public Project() {
    }

    private Project(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.image_path = builder.image_path;
    }

    public static class Builder {
        private int id;
        private String nombre;
        private String image_path;

        public Builder() {
            // Inicialización de valores predeterminados si es necesario
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder projectname(String projectname) {
            this.nombre = projectname;
            return this;
        }

        public Builder image(String image) {
            this.image_path = image;
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
        return nombre;
    }

    @Override
    public String getImage() {
        return image_path;
    }

    @Override
    public void setProjectname(String projectname) {
        this.nombre = projectname;
    }

    @Override
    public void setImage(String image) {
        this.image_path = image;
    }
}
