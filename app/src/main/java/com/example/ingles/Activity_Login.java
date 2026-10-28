package com.example.ingles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

public class Activity_Login extends AppCompatActivity {

    private EditText etClaveLogin, etPasswordLogin;
    private Button btnLogin;
    private TextView tvIrRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etClaveLogin = findViewById(R.id.etClaveLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvIrRegistro = findViewById(R.id.tvIrRegistro);

        // Evento para iniciar sesión
        btnLogin.setOnClickListener(v -> iniciarSesion());

        // Evento para ir a la pantalla de registro
        tvIrRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Login.this, Activity_Registro.class);
            startActivity(intent);
        });
    }

    private void iniciarSesion() {
        String identificador = etClaveLogin.getText() != null ? etClaveLogin.getText().toString().trim() : "";
        String contrasena = etPasswordLogin.getText() != null ? etPasswordLogin.getText().toString().trim() : "";

        if (identificador.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Ingresa tu correo/matrícula y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        // Deshabilitar el botón para evitar múltiples peticiones
        btnLogin.setEnabled(false);

        ApiService apiService = ApiClient.getClient(this);

        LoginRequest loginRequest = new LoginRequest(identificador, contrasena);

        Call<ApiResponse> call = apiService.loginAlumno(loginRequest);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                btnLogin.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();

                    if (apiResponse.getStatus() == 200) {
                        // Guardar el token para mantener la sesión abierta
                        guardarToken(apiResponse.getToken());

                        Toast.makeText(Activity_Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                        // Navegar a la pantalla de Configuraciones
                        Intent intent = new Intent(Activity_Login.this, Activity_Configuraciones.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Activity_Login.this, apiResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Activity_Login.this, "Credenciales incorrectas o error del servidor", Toast.LENGTH_LONG).show();
                    Log.e("ERROR_API", "Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                Toast.makeText(Activity_Login.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("ERROR_API", "Falla en Retrofit: " + t.getMessage());
            }
        });
    }

    private void guardarToken(String token) {
        SharedPreferences preferences = getSharedPreferences("AppInglesPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("JWT_TOKEN", token);
        editor.apply();
    }
}
