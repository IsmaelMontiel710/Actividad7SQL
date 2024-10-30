package com.example.actividad7sqlcrud;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

// Clase Details para mostrar la información detallada de un usuario
public class Details extends AppCompatActivity {

    // Declaración de TextViews para mostrar la información de usuario
    TextView txtid, txtphone, txtname, txtFirstSurname, txtAge, txtGender, txtBirthdate, txtHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configuración de la pantalla para habilitar diseño de borde a borde
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        setTitle("Detalle Usuario"); // Título de la actividad

        // Inicialización de los TextViews
        txtid = findViewById(R.id.txtid);
        txtphone = findViewById(R.id.txtphone);
        txtname = findViewById(R.id.txtname);
        txtFirstSurname = findViewById(R.id.txtFirstSurname);
        txtAge = findViewById(R.id.txtAge);
        txtGender = findViewById(R.id.txtGender);
        txtBirthdate = findViewById(R.id.txtBirthdate);
        txtHeight = findViewById(R.id.txtHeight);

        // Obtener el objeto User que fue enviado a esta actividad
        Bundle object = getIntent().getExtras();
        Users user = null;
        if (object != null) {
            // Deserializar el objeto "user" recibido
            user = (Users) object.getSerializable("user");

            // Mostrar la información del usuario en los TextViews
            txtid.setText(user.getId().toString()); // Muestra el ID del usuario
            txtname.setText(user.getName()); // Muestra el nombre del usuario
            txtphone.setText(user.getPhone()); // Muestra el teléfono
            txtFirstSurname.setText(user.getFirst_surname()); // Muestra el primer apellido
            txtAge.setText(user.getAge() + " años"); // Muestra la edad y añade "años"
            txtGender.setText(user.getGender()); // Muestra el género
            txtBirthdate.setText(user.getBirthdate()); // Muestra la fecha de nacimiento
            txtHeight.setText(user.getHeight() + " cm"); // Muestra la altura y añade "cm"
        }
    }
}
