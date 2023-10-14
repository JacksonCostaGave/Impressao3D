package com.example.impressao3d;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLClientInfoException;

public class CadastrarClienteActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    Button btnCadastrarCliente;
    EditText editTextNome, editTextEmail, editTextCelular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cliente);

        btnCadastrarCliente = (Button) findViewById(R.id.btnCadastrarCliente);
        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextCelular = (EditText) findViewById(R.id.editTextCelular);

        btnCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    public void cadastrar(){
        if(TextUtils.isEmpty(editTextNome.getText().toString())){
            editTextNome.setError("Digite um Nome!");
        } else if(TextUtils.isEmpty(editTextEmail.getText().toString())){
            editTextEmail.setError("Digite um Email!");
        } else if(TextUtils.isEmpty(editTextCelular.getText().toString())){
            editTextCelular.setError("Digite um Celular Ex. 00 00000-0000!");
        } else {
            try {
                bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
                String sql = "INSERT INTO pessoa (nome,email,celular) VALUES (?,?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, editTextNome.getText().toString());
                stmt.bindString(2, editTextEmail.getText().toString());
                stmt.bindString(3, editTextCelular.getText().toString());
                stmt.executeInsert();
                bancoDados.close();
                finish();
                Toast.makeText(this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}