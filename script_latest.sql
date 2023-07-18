DROP database jmatch;

CREATE DATABASE if NOT EXISTS jmatch;

USE jmatch;

CREATE TABLE
    if NOT EXISTS usuario (
        id INT PRIMARY key NOT NULL AUTO_INCREMENT,
        username VARCHAR(30) NOT NULL,
        nombre VARCHAR(50) NOT NULL,
        apellido VARCHAR(50) NOT NULL,
        correo VARCHAR(50) NOT NULL,
        passwd VARCHAR(80) NOT NULL,
        CONSTRAINT UNIQUE (correo)
    ) ENGINE = INNODB;

CREATE TABLE
    if NOT EXISTS categoria (
        id INT PRIMARY key NOT NULL AUTO_INCREMENT,
        nombre VARCHAR(50) NOT NULL,
        descripcion VARCHAR(100)
    ) ENGINE = INNODB;

CREATE TABLE
    if NOT EXISTS freelancer (
        id INT PRIMARY key NOT NULL AUTO_INCREMENT,
        username varchar(30) not null,
        id_categoria INT,
        nombre VARCHAR(50) NOT NULL,
        apellido VARCHAR(50) NOT NULL,
        foto VARCHAR(200),
        descripcion TEXT,
        correo VARCHAR(50) NOT NULL,
        passwd VARCHAR(80) NOT NULL,
        telefono VARCHAR(20),
        CONSTRAINT UNIQUE (correo),
        CONSTRAINT FOREIGN key (id_categoria) REFERENCES categoria (id)
    ) ENGINE = INNODB;

CREATE TABLE
    if NOT EXISTS portfolio (
        id INT PRIMARY key NOT NULL AUTO_INCREMENT,
        id_freelancer INT NOT NULL,
        descripcion text,
        CONSTRAINT FOREIGN key (id_freelancer) REFERENCES freelancer (id)
    ) ENGINE = INNODB;

CREATE TABLE
    if NOT EXISTS proyecto (
        id INT PRIMARY key NOT NULL AUTO_INCREMENT,
        id_portfolio INT NOT NULL,
        image_path VARCHAR(255),
        nombre VARCHAR(50),
        CONSTRAINT FOREIGN key (id_portfolio) REFERENCES portfolio (id)
    ) ENGINE = INNODB;

CREATE TABLE
    if NOT EXISTS valoracion (
        id INT PRIMARY key NOT NULL AUTO_INCREMENT,
        id_usuario INT NOT NULL,
        id_freelancer INT NOT NULL,
        rate_stars INT,
        comentario text,
        CONSTRAINT FOREIGN key (id_freelancer) REFERENCES freelancer (id),
        CONSTRAINT FOREIGN key (id_usuario) REFERENCES usuario (id)
    ) ENGINE = INNODB;