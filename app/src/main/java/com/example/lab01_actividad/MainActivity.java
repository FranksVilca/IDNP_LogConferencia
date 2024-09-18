package com.example.lab01_actividad;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtSurname, edtEmail, edtPhone;
    private Spinner spinnerBloodGroup;
    private Button btnSave, btnLoad;

    // Variables para almacenar los datos
    private String name, surname, email, phone, bloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los elementos del layout
        edtName = findViewById(R.id.edtName);
        edtSurname = findViewById(R.id.edtSurname);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);

        // Adaptador para el spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(adapter);

        // Evento al hacer clic en el botón Guardar
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        // Evento al hacer clic en el botón Leer
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void saveData() {
        // Guardar los datos en variables
        phone = edtPhone.getText().toString();
        if (!phone.matches("\\d+")) {
            Log.e("MainActivity", "Número de teléfono inválido. Solo se permiten números.");
            Toast.makeText(this, "Número de teléfono inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        name = edtName.getText().toString();
        surname = edtSurname.getText().toString();
        email = edtEmail.getText().toString();
        bloodGroup = spinnerBloodGroup.getSelectedItem().toString();

        // Crear cadena de datos
        String data = name + "," + surname + "," + email + "," + phone + "," + bloodGroup + "\n";

        // Guardar en un archivo de texto
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + "/data.txt", true))) {
            writer.write(data);
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("MainActivity", "Error al escribir en el archivo", e);
        }
    }

    private void loadData() {
        // Leer los datos del archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilesDir() + "/data.txt"))) {
            String line;
            StringBuilder allData = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                allData.append(line).append("\n");
            }

            // Mostrar los datos por consola y en un Toast
            Log.d("MainActivity", "Datos leídos:\n" + allData.toString());
            Toast.makeText(this, "Datos:\n" + allData.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("MainActivity", "Error al leer del archivo", e);
        }
    }
}
