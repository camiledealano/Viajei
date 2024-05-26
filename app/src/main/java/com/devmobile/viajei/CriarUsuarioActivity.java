package com.devmobile.viajei;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devmobile.viajei.database.dao.CriarUsuarioDAO;
import com.devmobile.viajei.database.model.UsuarioModel;

public class CriarUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText telefone;
    private EditText senha;

    private CriarUsuarioDAO criarUsuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_usuario);

        nome = findViewById(R.id.nome);
        email = findViewById(R.id.email);
        telefone = findViewById(R.id.telefone);
        senha = findViewById(R.id.senha);

        Button btnCriar = findViewById(R.id.btnCriar);

        btnCriar.setOnClickListener(v -> {
            String nomeText = nome.getText().toString();
            String emailText = email.getText().toString();
            String telefoneText = telefone.getText().toString();
            String senhaText = senha.getText().toString();

            if (nomeText.isEmpty() || (emailText.isEmpty()) || telefoneText.isEmpty() || senhaText.isEmpty()) {
                Toast.makeText(CriarUsuarioActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            } else if (!isValidEmail(emailText)) {
                Toast.makeText(CriarUsuarioActivity.this, "Por favor, insira um email válido", Toast.LENGTH_SHORT).show();
                return;
            }

            UsuarioModel usuarioModel = new UsuarioModel(
                    nomeText,
                    emailText,
                    telefoneText,
                    senhaText
            );

            try {
                criarUsuarioDAO = new CriarUsuarioDAO(CriarUsuarioActivity.this);
                criarUsuarioDAO.insert(usuarioModel);

                Toast.makeText(CriarUsuarioActivity.this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CriarUsuarioActivity.this, HomeActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(CriarUsuarioActivity.this, "Erro ao inserir usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}