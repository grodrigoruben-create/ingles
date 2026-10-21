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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Registro extends AppCompatActivity {

    private EditText etName, etMatricula, etEmail, etPassword;
    private Spinner spinnerCarrera;
    private Button btnRegister;
    private TextView tvLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.et_name);
        etMatricula = findViewById(R.id.et_matricula);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        spinnerCarrera = findViewById(R.id.spinner_carrera);
        btnRegister = findViewById(R.id.btn_register);
        tvLoginLink = findViewById(R.id.tv_login_link);

        // Setup Spinner
        String[] carreras = {"Ingeniería en Sistemas Computacionales", "Ingeniería Industrial", "Licenciatura en Lenguas", "Administración de Empresas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carreras);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarrera.setAdapter(adapter);

        btnRegister.setOnClickListener(v -> registrarAlumno());

        tvLoginLink.setOnClickListener(v -> finish());
    }

    private void registrarAlumno() {
        String nombre = etName.getText() != null ? etName.getText().toString().trim() : "";
        String matricula = etMatricula.getText() != null ? etMatricula.getText().toString().trim() : "";
        String correo = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String carrera = spinnerCarrera.getSelectedItem() != null ? spinnerCarrera.getSelectedItem().toString() : "";
        String contrasena = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        if (nombre.isEmpty() || matricula.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Alumno alumno = new Alumno(nombre, matricula, correo, carrera, contrasena);

        ApiService apiService = ApiClient.getClient();
        Call<ApiResponse> call = apiService.gardaAlumno("", alumno);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    Toast.makeText(Activity_Registro.this, apiResponse.getMessage(), Toast.LENGTH_LONG).show();
                    if (apiResponse.getStatus() == 200 || apiResponse.getStatus() == 201) {
                        finish();
                    }
                } else {
                    Toast.makeText(Activity_Registro.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(Activity_Registro.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
