package com.example.ingles;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Configuraciones extends AppCompatActivity {

    private TextView tvHolaNombre;
    private EditText etHorarioInicio, etHorarioFin, etTiempoAparicion, etTiempoExamen;
    private Spinner spinnerCategoriaConfig;
    private Button btnEnviarConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuraciones);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.config_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vincular vistas
        tvHolaNombre = findViewById(R.id.tvHolaNombre);
        etHorarioInicio = findViewById(R.id.etHorarioInicio);
        etHorarioFin = findViewById(R.id.etHorarioFin);
        etTiempoAparicion = findViewById(R.id.etTiempoAparicion);
        etTiempoExamen = findViewById(R.id.etTiempoExamen);
        spinnerCategoriaConfig = findViewById(R.id.spinnerCategoriaConfig);
        btnEnviarConfig = findViewById(R.id.btnEnviarConfig);

        // Llenar el Spinner de Categorías (puedes cambiar estas opciones luego)
        String[] categorias = {"Categoría A", "Categoría B", "Categoría C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoriaConfig.setAdapter(adapter);

        // Evento del botón enviar
        btnEnviarConfig.setOnClickListener(v -> {
            Toast.makeText(this, "Configuraciones guardadas", Toast.LENGTH_SHORT).show();
            // Aquí luego agregarás la lógica para enviar a tu API
        });
    }
}
