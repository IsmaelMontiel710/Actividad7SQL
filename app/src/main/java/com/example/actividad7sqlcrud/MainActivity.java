package com.example.actividad7sqlcrud;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Componentes de la interfaz de usuario
    EditText fieldid, name, phone, first_surname, age, gender, birthdate, height;
    Button insert1, insert2, search1, search2, edit, delete, view;
    Button btnSearchFirstSurname, btnSearchAge, btnSearchHeight, btnViewSorted;

    // Conexión a la base de datos
    Connect connect;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Users Control");

        // Inicialización de los componentes de la interfaz de usuario
        fieldid = findViewById(R.id.fieldid);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        first_surname = findViewById(R.id.first_surname);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        birthdate = findViewById(R.id.birthdate);
        height = findViewById(R.id.height);

        // Inicialización de botones
        insert1 = findViewById(R.id.insert1);
        insert2 = findViewById(R.id.insert2);
        search1 = findViewById(R.id.search1);
        search2 = findViewById(R.id.search2);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        view = findViewById(R.id.view);
        btnSearchFirstSurname = findViewById(R.id.btn_search_first_surname);
        btnSearchAge = findViewById(R.id.btn_search_age);
        btnSearchHeight = findViewById(R.id.btn_search_height);
        btnViewSorted = findViewById(R.id.btn_view_sorted); // Botón para ver lista ordenada

        // Asignación de listeners para cada botón
        insert1.setOnClickListener(this);
        insert2.setOnClickListener(this);
        search1.setOnClickListener(this);
        search2.setOnClickListener(this);
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        view.setOnClickListener(this);
        btnSearchFirstSurname.setOnClickListener(this);
        btnSearchAge.setOnClickListener(this);
        btnSearchHeight.setOnClickListener(this);
        btnViewSorted.setOnClickListener(this);

        // Inicialización de la conexión a la base de datos
        connect = new Connect(this, Variable.NAME_DB, null, 1);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Manejo de cada acción de los botones
        if (id == R.id.insert1) {
            insert1(); // Insertar usando ContentValues
        } else if (id == R.id.insert2) {
            insert2(); // Insertar usando una sentencia SQL en bruto
        } else if (id == R.id.search1) {
            search1(); // Buscar usando el método query
        } else if (id == R.id.search2) {
            search2(); // Buscar usando SQL en bruto
        } else if (id == R.id.edit) {
            edit(); // Editar detalles de usuario
        } else if (id == R.id.delete) {
            delete(); // Eliminar usuario por ID
        } else if (id == R.id.view) {
            i = new Intent(MainActivity.this, List.class);
            startActivity(i); // Abre la actividad para mostrar todos los usuarios
            clearFields(); // Limpia los campos después de la acción
        } else if (id == R.id.btn_search_first_surname) {
            searchByAttribute(Variable.FIELD_FIRST_SURNAME, first_surname.getText().toString());
        } else if (id == R.id.btn_search_age) {
            searchByAttribute(Variable.FIELD_AGE, age.getText().toString());
        } else if (id == R.id.btn_search_height) {
            searchByAttribute(Variable.FIELD_HEIGHT, height.getText().toString());
        } else if (id == R.id.btn_view_sorted) {
            // Abre la actividad de lista ordenada
            i = new Intent(MainActivity.this, SortedListActivity.class);
            startActivity(i);
            clearFields();
        }
    }

    // Eliminar usuario por ID y limpiar campos
    private void delete() {
        SQLiteDatabase db = connect.getWritableDatabase();
        String[] param = {fieldid.getText().toString()};
        int n = db.delete(Variable.NAME_TABLE, Variable.FIELD_ID + "=?", param);
        Toast.makeText(this, "User deleted: " + n, Toast.LENGTH_LONG).show();
        clearFields(); // Limpia los campos de entrada
        db.close();
    }

    // Editar detalles del usuario y limpiar campos
    private void edit() {
        SQLiteDatabase db = connect.getWritableDatabase();
        String[] param = {fieldid.getText().toString()};
        ContentValues values = new ContentValues();
        values.put(Variable.FIELD_NAME, name.getText().toString());
        values.put(Variable.FIELD_PHONE, phone.getText().toString());
        values.put(Variable.FIELD_FIRST_SURNAME, first_surname.getText().toString());
        values.put(Variable.FIELD_AGE, age.getText().toString());
        values.put(Variable.FIELD_GENDER, gender.getText().toString());
        values.put(Variable.FIELD_BIRTHDATE, birthdate.getText().toString());
        values.put(Variable.FIELD_HEIGHT, height.getText().toString());

        db.update(Variable.NAME_TABLE, values, Variable.FIELD_ID + "=?", param);
        Toast.makeText(this, "User data updated", Toast.LENGTH_LONG).show();
        clearFields(); // Limpia los campos de entrada
        db.close();
    }

    // Buscar por ID usando método query
    private void search1() {
        SQLiteDatabase db = connect.getReadableDatabase();
        String[] param = {fieldid.getText().toString()};
        String[] fields = {Variable.FIELD_NAME, Variable.FIELD_PHONE, Variable.FIELD_FIRST_SURNAME,
                Variable.FIELD_AGE, Variable.FIELD_GENDER, Variable.FIELD_BIRTHDATE, Variable.FIELD_HEIGHT};
        try {
            Cursor cursor = db.query(Variable.NAME_TABLE, fields, Variable.FIELD_ID + "=?", param, null, null, null);
            if (cursor.moveToFirst()) {
                populateFields(cursor); // Llena los campos con los datos obtenidos del cursor
            } else {
                Toast.makeText(this, "User does not exist", Toast.LENGTH_LONG).show();
                clearFields(); // Limpia los campos si el usuario no existe
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error searching user", Toast.LENGTH_SHORT).show();
            clearFields(); // Limpia los campos en caso de error
            throw new RuntimeException(e);
        }
        db.close();
    }

    // Buscar por ID usando SQL en bruto y limpiar campos después de la acción
    private void search2() {
        SQLiteDatabase db = connect.getReadableDatabase();
        String[] param = {fieldid.getText().toString()};
        try {
            Cursor cursor = db.rawQuery("SELECT " + Variable.FIELD_NAME + ", " + Variable.FIELD_PHONE + ", "
                    + Variable.FIELD_FIRST_SURNAME + ", " + Variable.FIELD_AGE + ", "
                    + Variable.FIELD_GENDER + ", " + Variable.FIELD_BIRTHDATE + ", "
                    + Variable.FIELD_HEIGHT + " FROM " + Variable.NAME_TABLE + " WHERE " + Variable.FIELD_ID + "=?", param);
            if (cursor.moveToFirst()) {
                populateFields(cursor); // Llena los campos con los datos obtenidos del cursor
            } else {
                Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
                clearFields(); // Limpia los campos si el usuario no existe
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error searching user", Toast.LENGTH_SHORT).show();
            clearFields(); // Limpia los campos en caso de error
            throw new RuntimeException(e);
        }
        db.close();
    }

    /**
     * Diferencia entre search1 y search2:
     *
     * - search1: Usa el método `query()` de `SQLiteDatabase`, lo que simplifica la consulta al separar claramente
     *            los parámetros y las columnas. Este método es más seguro ya que evita la concatenación directa
     *            de cadenas y ayuda a prevenir problemas de seguridad como inyecciones SQL.
     *            Es adecuado para consultas simples y estructuradas.
     *            Ejemplo: `db.query(NOMBRE_TABLA, columnas, seleccion, argumentosSeleccion, null, null, null);`
     *
     * - search2: Usa `rawQuery()`, que permite realizar consultas SQL en bruto definidas como una cadena completa.
     *            Es útil para consultas SQL personalizadas o complejas, pero requiere cuidado para evitar problemas
     *            de seguridad, ya que la consulta se construye manualmente. Puede ser menos seguro frente a inyecciones SQL.
     *            Ejemplo: `db.rawQuery("SELECT * FROM ...", argumentosSeleccion);`
     *
     * En resumen, `search1` es seguro y adecuado para consultas estructuradas, mientras que `search2` proporciona
     * más flexibilidad en casos donde se necesita construir consultas complejas.
     */


    // Insertar usuario usando ContentValues y limpiar campos
    private void insert1() {
        SQLiteDatabase db = connect.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Variable.FIELD_NAME, name.getText().toString());
        values.put(Variable.FIELD_PHONE, phone.getText().toString());
        values.put(Variable.FIELD_FIRST_SURNAME, first_surname.getText().toString());
        values.put(Variable.FIELD_AGE, age.getText().toString());
        values.put(Variable.FIELD_GENDER, gender.getText().toString());
        values.put(Variable.FIELD_BIRTHDATE, birthdate.getText().toString());
        values.put(Variable.FIELD_HEIGHT, height.getText().toString());

        long id = db.insert(Variable.NAME_TABLE, Variable.FIELD_ID, values);
        Toast.makeText(this, "id: " + id, Toast.LENGTH_LONG).show();
        clearFields(); // Limpia los campos de entrada
        db.close();
    }

    // Insertar usuario usando una sentencia SQL en bruto y limpiar campos
    private void insert2() {
        SQLiteDatabase db = connect.getWritableDatabase();
        String insert = "INSERT INTO " + Variable.NAME_TABLE + " (" + Variable.FIELD_NAME + ", "
                + Variable.FIELD_PHONE + ", " + Variable.FIELD_FIRST_SURNAME + ", " + Variable.FIELD_AGE + ", "
                + Variable.FIELD_GENDER + ", " + Variable.FIELD_BIRTHDATE + ", " + Variable.FIELD_HEIGHT + ") VALUES ('"
                + name.getText().toString() + "', '" + phone.getText().toString() + "', '"
                + first_surname.getText().toString() + "', '" + age.getText().toString() + "', '"
                + gender.getText().toString() + "', '" + birthdate.getText().toString() + "', '"
                + height.getText().toString() + "')";
        db.execSQL(insert);
        Toast.makeText(this, "User data inserted", Toast.LENGTH_LONG).show();
        clearFields(); // Limpia los campos de entrada
        db.close();
    }

    /**
     * Diferencia entre insert1 e insert2:
     *
     * - insert1: Utiliza `ContentValues`, un método seguro y estructurado para insertar datos en la base de datos.
     *            Este enfoque es más seguro porque evita problemas de inyección SQL y es fácil de mantener.
     *            Es recomendable usar `insert1` cuando se trabaja directamente con datos de la interfaz de usuario.
     *            Ejemplo: `db.insert(NOMBRE_TABLA, null, values);`
     *
     * - insert2: Utiliza una consulta SQL en bruto (raw SQL), construida manualmente como una cadena de texto.
     *            Este método permite más personalización en la consulta, pero es menos seguro, ya que puede ser vulnerable
     *            a inyecciones SQL si no se maneja correctamente. Es útil en casos donde se necesita un mayor control
     *            sobre la consulta.
     *            Ejemplo: `db.execSQL("INSERT INTO ...");`
     *
     * En resumen, `insert1` es más seguro y recomendable para la mayoría de los casos, mientras que `insert2` es útil
     * cuando se requiere flexibilidad adicional.
     */


    // Buscar por cualquier atributo y manejar múltiples resultados
    private void searchByAttribute(String field, String value) {
        SQLiteDatabase db = connect.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Variable.NAME_TABLE + " WHERE " + field + " = ?", new String[]{value});

        ArrayList<Users> results = new ArrayList<>();
        while (cursor.moveToNext()) {
            Users user = new Users();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setPhone(cursor.getString(2));
            user.setFirst_surname(cursor.getString(3));
            user.setAge(cursor.getInt(4));
            user.setGender(cursor.getString(5));
            user.setBirthdate(cursor.getString(6));
            user.setHeight(cursor.getDouble(7));
            results.add(user);
        }
        cursor.close();
        db.close();

        if (results.size() == 1) {
            Intent intent = new Intent(this, Details.class);
            intent.putExtra("user", results.get(0));
            startActivity(intent);
        } else if (results.size() > 1) {
            Intent intent = new Intent(this, SearchResultsActivity.class);
            intent.putExtra("results", results);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }
        clearFields(); // Limpia los campos después de la búsqueda
    }

    // Llena los campos de entrada con los datos obtenidos de un cursor
    private void populateFields(Cursor cursor) {
        name.setText(cursor.getString(0));
        phone.setText(cursor.getString(1));
        first_surname.setText(cursor.getString(2));
        age.setText(cursor.getString(3));
        gender.setText(cursor.getString(4));
        birthdate.setText(cursor.getString(5));
        height.setText(cursor.getString(6));
    }

    // Limpia todos los campos de entrada
    private void clearFields() {
        name.setText("");
        phone.setText("");
        first_surname.setText("");
        age.setText("");
        gender.setText("");
        birthdate.setText("");
        height.setText("");
        fieldid.setText("");
    }
}
