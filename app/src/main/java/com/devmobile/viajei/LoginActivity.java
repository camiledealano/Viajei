package com.devmobile.viajei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devmobile.viajei.database.dao.CriarUsuarioDAO;
import com.devmobile.viajei.database.model.UsuarioModel;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.loginEmail);
        campoSenha = findViewById(R.id.loginSenha);

        TextView textViewCriarConta = findViewById(R.id.criarConta);
        textViewCriarConta.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, CriarUsuarioActivity.class);
            startActivity(intent);
        });

        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> fazerLogin());
    }

    private void fazerLogin() {
        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, digite o email e a senha", Toast.LENGTH_SHORT).show();
            return;
        }

        CriarUsuarioDAO criarUsuarioDAO = new CriarUsuarioDAO(LoginActivity.this);
        UsuarioModel usuario = criarUsuarioDAO.Select(email, senha);

        if (usuario == null) {
            Toast.makeText(this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!usuario.getSenha().equals(senha)) {
            Toast.makeText(this, "Senha inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("nomeUsuario", usuario.getNome());
        editor.putLong("idUsuario", usuario.getId());
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
