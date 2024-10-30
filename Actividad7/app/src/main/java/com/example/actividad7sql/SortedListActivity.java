package com.example.actividad7sql;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// Clase SortedListActivity para mostrar una lista de usuarios ordenada por nombre
public class SortedListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // Declaración de componentes y listas
    ListView sortedListView; // ListView para mostrar la lista de usuarios ordenada
    ArrayList<String> userList; // Lista de cadenas que mostrará información básica de cada usuario
    ArrayList<Users> dataUser; // Lista de objetos Users con todos los datos de cada usuario
    Connect connect; // Objeto de conexión a la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_list);
        setTitle("Lista Ordenada por Nombre"); // Título de la actividad

        // Inicialización del ListView usando el ID del diseño XML
        sortedListView = findViewById(R.id.sorted_list_view);

        // Llama al método para mostrar la lista de usuarios ordenada
        displaySortedList();

        // Configura el adaptador para llenar el ListView con los datos de userList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        sortedListView.setAdapter(adapter);

        // Asigna el listener para manejar los clics en los elementos de la lista
        sortedListView.setOnItemClickListener(this);
    }

    // Método para obtener y mostrar la lista de usuarios ordenada alfabéticamente por nombre
    private void displaySortedList() {
        connect = new Connect(this, Variable.NAME_DB, null, 1); // Conexión a la base de datos
        SQLiteDatabase db = connect.getReadableDatabase(); // Base de datos en modo lectura
        dataUser = new ArrayList<>(); // Inicializa la lista de objetos Users
        userList = new ArrayList<>(); // Inicializa la lista de cadenas para el ListView

        // Consulta SQL para seleccionar todos los usuarios y ordenarlos por nombre
        Cursor cursor = db.rawQuery("SELECT * FROM " + Variable.NAME_TABLE + " ORDER BY " + Variable.FIELD_NAME, null);
        while (cursor.moveToNext()) {
            // Crea un objeto Users y asigna los valores de cada columna a sus respectivos atributos
            Users user = new Users();
            user.setId(cursor.getInt(0)); // Columna 0: ID del usuario
            user.setName(cursor.getString(1)); // Columna 1: Nombre
            user.setPhone(cursor.getString(2)); // Columna 2: Teléfono
            user.setFirst_surname(cursor.getString(3)); // Columna 3: Primer apellido
            user.setAge(cursor.getInt(4)); // Columna 4: Edad
            user.setGender(cursor.getString(5)); // Columna 5: Género
            user.setBirthdate(cursor.getString(6)); // Columna 6: Fecha de nacimiento
            user.setHeight(cursor.getDouble(7)); // Columna 7: Estatura

            // Agrega el objeto usuario a dataUser para mantener todos los datos
            dataUser.add(user);

            // Agrega una representación en texto del usuario a userList para mostrar en el ListView
            userList.add(user.getId() + " - " + user.getName());
        }
        cursor.close(); // Cierra el cursor después de leer los datos
        db.close(); // Cierra la base de datos
    }

    // Método que se ejecuta cuando se hace clic en un elemento de la lista
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Obtiene el usuario seleccionado usando la posición en dataUser
        Users selectedUser = dataUser.get(position);

        // Crea un Intent para abrir la actividad Details y pasar el usuario seleccionado
        Intent intent = new Intent(SortedListActivity.this, Details.class);
        intent.putExtra("user", selectedUser); // Agrega el objeto usuario al Intent
        startActivity(intent); // Inicia la actividad Details para mostrar los detalles del usuario
    }
}
