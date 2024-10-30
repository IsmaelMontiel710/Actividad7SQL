package com.example.actividad7sqlcrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Base de datos local SQLite para la aplicación móvil
public class Connect extends SQLiteOpenHelper {

    // Constructor que inicializa SQLiteOpenHelper con el contexto, nombre de la base de datos, fábrica de cursores y versión
    public Connect(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Método que se ejecuta solo la primera vez que se crea la base de datos
        // Ejecuta la sentencia SQL definida en Variable.CREATE_TABLE para configurar la estructura de la tabla
        db.execSQL(Variable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Método que maneja la actualización de la base de datos cuando el número de versión incrementa
        // Primero elimina la tabla existente usando el comando SQL en Variable.DELETE_TABLE
        db.execSQL(Variable.DELETE_TABLE);
        // Luego llama a onCreate() para recrear la tabla con la estructura actualizada
        onCreate(db);
    }
}
