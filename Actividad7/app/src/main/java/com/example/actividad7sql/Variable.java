package com.example.actividad7sql;

// Clase Variable para definir constantes de la base de datos y comandos SQL
public class Variable {
    // Nombre de la base de datos
    public static final String NAME_DB = "db_user";

    // Nombre de la tabla de usuarios
    public static final String NAME_TABLE = "users";

    // Nombres de los campos de la tabla
    public static final String FIELD_ID = "id"; // ID único de cada usuario, clave primaria
    public static final String FIELD_NAME = "name"; // Nombre del usuario
    public static final String FIELD_PHONE = "phone"; // Teléfono del usuario
    public static final String FIELD_FIRST_SURNAME = "first_surname"; // Primer apellido del usuario
    public static final String FIELD_AGE = "age"; // Edad del usuario
    public static final String FIELD_GENDER = "gender"; // Género del usuario
    public static final String FIELD_BIRTHDATE = "birthdate"; // Fecha de nacimiento del usuario
    public static final String FIELD_HEIGHT = "height"; // Altura del usuario en cm


    // Instrucción SQL para crear la tabla con todos los campos necesarios
    public static final String CREATE_TABLE = "CREATE TABLE " + NAME_TABLE + " (" +
            FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Campo ID autoincremental y clave primaria
            FIELD_NAME + " TEXT, " + // Campo para almacenar el nombre del usuario
            FIELD_PHONE + " TEXT, " + // Campo para almacenar el teléfono del usuario
            FIELD_FIRST_SURNAME + " TEXT, " + // Campo para almacenar el primer apellido
            FIELD_AGE + " INTEGER, " + // Campo para almacenar la edad
            FIELD_GENDER + " TEXT, " + // Campo para almacenar el género
            FIELD_BIRTHDATE + " TEXT, " + // Campo para almacenar la fecha de nacimiento
            FIELD_HEIGHT + " REAL)"; // Campo para almacenar la altura

    // Instrucción SQL para eliminar la tabla si existe, útil en actualizaciones
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + NAME_TABLE;
}
