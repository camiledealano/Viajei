package com.devmobile.viajei;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.devmobile.viajei.database.DBOpenHelper;
import com.devmobile.viajei.database.model.UsuarioModel;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button botaoLogin;
    private DBOpenHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.editTextEmail);
        campoSenha = findViewById(R.id.editTextSenha);
        botaoLogin = findViewById(R.id.buttonLogin);
        databaseHelper = new DBOpenHelper(this);

        botaoLogin.setOnClickListener(v -> fazerLogin());
    }

    private void fazerLogin() {
        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, digite o email e a senha", Toast.LENGTH_SHORT).show();
            return;
        }

        DBOpenHelper dbHelper = new DBOpenHelper(this);
        UsuarioModel usuario = dbHelper.getUsuarioPorEmail(email);


        if (usuario == null) {
            Toast.makeText(this, "Usuário com este email não existe", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!usuario.getSenha().equals(senha)) {
            Toast.makeText(this, "Senha inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show();

    }
}
