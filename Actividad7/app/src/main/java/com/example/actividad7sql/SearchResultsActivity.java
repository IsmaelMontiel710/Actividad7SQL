package com.example.actividad7sql;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// Clase SearchResultsActivity para mostrar los resultados de una búsqueda de usuarios
public class SearchResultsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView resultsListView; // ListView para mostrar los resultados de la búsqueda
    ArrayList<Users> searchResults; // Lista de objetos Users que contiene los resultados de la búsqueda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results); // Establece el diseño de la actividad

        // Inicializa el ListView con el ID definido en el archivo de diseño XML
        resultsListView = findViewById(R.id.results_list_view);

        // Obtiene la lista de resultados de la búsqueda desde el Intent que inició esta actividad
        searchResults = (ArrayList<Users>) getIntent().getSerializableExtra("results");

        // Verificar si searchResults está vacío o nulo
        if (searchResults == null || searchResults.isEmpty()) {
            Log.e("SearchResultsActivity", "No se encontraron resultados de búsqueda.");
            return;
        }

        // Obtiene el campo de búsqueda desde el Intent y elimina espacios en blanco
        String searchField = getIntent().getStringExtra("searchField");
        if (searchField == null) {
            searchField = ""; // Asignar un valor vacío si es null
        }

        Log.d("SearchResultsActivity", "Campo de búsqueda recibido: " + searchField);

        // Crea una lista de cadenas (String) para mostrar los resultados en formato de texto en el ListView
        ArrayList<String> displayList = new ArrayList<>();
        for (Users user : searchResults) {

            displayList.add(user.getId() + " - " + user.getName());
        }

        // Configura el adaptador para llenar el ListView con los datos de displayList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        resultsListView.setAdapter(adapter); // Establece el adaptador en el ListView

        // Asigna el listener para manejar clics en cada elemento de la lista
        resultsListView.setOnItemClickListener(this);
    }

    // Método que se ejecuta cuando el usuario hace clic en un elemento de la lista de resultados
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Obtiene el usuario seleccionado usando la posición del clic en searchResults
        Users selectedUser = searchResults.get(position);

        // Crea un Intent para abrir la actividad Details y mostrar la información del usuario seleccionado
        Intent intent = new Intent(this, Details.class);
        intent.putExtra("user", selectedUser); // Agrega el usuario seleccionado al Intent como extra
        startActivity(intent); // Inicia la actividad Details
    }
}
