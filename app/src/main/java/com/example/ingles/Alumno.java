package com.example.ingles;

public class Alumno {
    private String nombre,matricula, correo, carrera, contrasena;

    public Alumno(String nombre, String matricula, String correo, String carrera, String contrasena) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.correo = correo;
        this.carrera = carrera;
        this.contrasena = contrasena;
    }
    // Getter y Setter
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getContrasena() {
        return contrasena;
    }
}

