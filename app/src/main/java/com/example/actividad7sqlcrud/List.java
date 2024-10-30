package com.example.actividad7sqlcrud;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// Clase List para mostrar la lista de usuarios en la base de datos
public class List extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView list; // ListView para mostrar los usuarios
    ArrayList<String> userlist; // Lista de cadenas para mostrar datos en la ListView
    ArrayList<Users> datauser; // ArrayList para almacenar objetos de la clase Users
    Connect connect; // Conexión a la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Habilitar diseño de borde a borde
        setContentView(R.layout.activity_list);
        setTitle("View User"); // Título de la actividad
        list = findViewById(R.id.list); // Inicializar el ListView

        // Llamamos al método para mostrar los datos de usuarios
        display();

        // Configuramos el adaptador para mostrar los datos en la ListView
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userlist);
        list.setAdapter(aa);

        // Asignamos el listener para manejar clics en los elementos de la lista
        list.setOnItemClickListener(this);
    }

    // Método para mostrar la lista de usuarios obtenidos de la base de datos
    private void display() {
        connect = new Connect(this, Variable.NAME_DB, null, 1); // Inicializar la conexión a la base de datos
        SQLiteDatabase db = connect.getReadableDatabase(); // Obtener base de datos en modo lectura
        Users user; // Objeto de la clase Users para almacenar temporalmente cada usuario
        datauser = new ArrayList<>(); // Inicializar lista para almacenar los usuarios

        // Consulta SQL para obtener todos los usuarios de la tabla
        Cursor cursor = db.rawQuery("SELECT * FROM " + Variable.NAME_TABLE, null);
        while (cursor.moveToNext()) {
            user = new Users(); // Crear un nuevo objeto usuario
            user.setId(cursor.getInt(0)); // Asigna el ID del usuario
            user.setName(cursor.getString(1)); // Asigna el nombre del usuario
            user.setPhone(cursor.getString(2)); // Asigna el teléfono
            user.setFirst_surname(cursor.getString(3)); // Asigna el primer apellido
            user.setAge(cursor.getInt(4)); // Asigna la edad
            user.setGender(cursor.getString(5)); // Asigna el género
            user.setBirthdate(cursor.getString(6)); // Asigna la fecha de nacimiento
            user.setHeight(cursor.getDouble(7)); // Asigna la estatura

            // Agrega el objeto usuario a la lista datauser
            datauser.add(user);
        }
        cursor.close(); // Cierra el cursor para liberar recursos

        // Llama a addlist() para llenar la lista userlist con cadenas formateadas para la ListView
        addlist();
        db.close(); // Cierra la base de datos
    }

    // Método para convertir la lista de objetos Users en una lista de cadenas para la ListView
    private void addlist() {
        userlist = new ArrayList<>(); // Inicializa la lista de cadenas
        for (int i = 0; i < datauser.size(); i++) {
            Users user = datauser.get(i); // Obtiene el usuario en la posición i
            // Agrega una cadena formateada con la información del usuario (ID, nombre y edad)
            userlist.add(user.getId() + " - " + user.getName() + " - " + user.getAge() + " years");
        }
    }

    // Método para manejar el clic en un elemento de la lista
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Obtiene el usuario correspondiente a la posición seleccionada en la lista
        Users users = datauser.get(position);

        // Crea un Intent para abrir la actividad Details y mostrar los detalles del usuario seleccionado
        Intent ii = new Intent(this, Details.class);

        // Crea un Bundle para enviar el objeto usuario como un extra
        Bundle b = new Bundle();
        b.putSerializable("user", users); // Empaquetamos el objeto usuario en el Bundle
        ii.putExtras(b); // Agrega el Bundle al Intent
        startActivity(ii); // Inicia la actividad Details
    }
}
