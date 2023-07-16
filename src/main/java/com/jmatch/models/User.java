package com.jmatch.models;

import com.jmatch.interfaces.UserRelated;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class User implements UserRelated {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    protected String nombre;
    protected String apellido;
    protected String correo;
    protected String passwd;

    public User() {
    }


    private User(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.setUsername(builder.username);
        this.correo = builder.correo;
        this.passwd = builder.passwd;
    }

    public static class Builder {
        private int id;
        private String nombre;
        private String apellido;
        private String username;
        private String correo;
        private String passwd;

        public Builder() {
            // Inicialización de valores predeterminados si es necesario
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder apellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder correo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder passwd(String passwd) {
            this.passwd = passwd;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getApellido() {
        return apellido;
    }

    @Override
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String getCorreo() {
        return correo;
    }

    @Override
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String getPasswd() {
        return passwd;
    }

    @Override
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
