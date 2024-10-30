package com.example.actividad7sqlcrud;

import java.io.Serializable;

// Clase Users que representa a un usuario y es Serializable para transferir entre actividades
public class Users implements Serializable {
    // Campos de los usuarios
    private Integer id; // ID único del usuario
    private String name; // Nombre del usuario
    private String phone; // Teléfono del usuario
    private String first_surname; // Primer apellido del usuario
    private Integer age; // Edad del usuario
    private String gender; // Género del usuario
    private String birthdate; // Fecha de nacimiento del usuario
    private Double height; // Altura del usuario en centímetros

    // Constructor con todos los campos, usado para crear objetos Users con datos específicos
    public Users(String phone, String name, String first_surname, Integer id, Integer age, String gender, String birthdate, Double height) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.first_surname = first_surname;
        this.age = age;
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
    }

    // Constructor vacío, necesario para instanciar un objeto sin definir sus atributos al momento de crearlo
    public Users() {
    }

    // Getters y Setters para cada campo (permite acceder y modificar cada atributo del usuario)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirst_surname() {
        return first_surname;
    }

    public void setFirst_surname(String first_surname) {
        this.first_surname = first_surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
