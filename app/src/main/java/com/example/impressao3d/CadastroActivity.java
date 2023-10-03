package com.example.impressao3d;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {
    SQLiteDatabase bancoDados;
    EditText editTextUsuario,editTextSenha;
    Button buttonEntrar, buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
        editTextSenha = (EditText) findViewById(R.id.editTextSenha);
        buttonCadastrar = (Button) findViewById(R.id.buttonEntrarLogin);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    public void cadastrar(){
        if(TextUtils.isEmpty(editTextUsuario.getText().toString())){
            editTextUsuario.setError("Campo obrigatório!");
        } else if(TextUtils.isEmpty(editTextSenha.getText().toString())){
            editTextSenha.setError("Campo obrigatório!");
        } else {
            try {
                bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
                String sql = "INSERT INTO usuario (user,senha) VALUES (?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, editTextUsuario.getText().toString());
                stmt.bindString(2, editTextSenha.getText().toString());
                stmt.executeInsert();
                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}