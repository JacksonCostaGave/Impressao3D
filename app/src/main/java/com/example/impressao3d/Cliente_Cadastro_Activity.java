package com.example.impressao3d;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cliente_Cadastro_Activity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    Button buttonCadastrarCliente, btnRetornar2;
    EditText editTextNome3, editTextEmail3, editTextCelular3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_cadastrar);

        buttonCadastrarCliente = (Button) findViewById(R.id.buttonCadastrarCliente);
        btnRetornar2 = (Button) findViewById(R.id.btnRetornar2);
        editTextNome3 = (EditText) findViewById(R.id.editTextNome3);
        editTextEmail3 = (EditText) findViewById(R.id.editTextEmail3);
        editTextCelular3 = (EditText) findViewById(R.id.editTextCelular3);

        btnRetornar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    public void cadastrar(){
        if(TextUtils.isEmpty(editTextNome3.getText().toString())){
            editTextNome3.setError("Digite um Nome!");
        } else if(TextUtils.isEmpty(editTextEmail3.getText().toString())){
            editTextEmail3.setError("Digite um Email!");
        } else if(TextUtils.isEmpty(editTextCelular3.getText().toString())){
            editTextCelular3.setError("Digite um Celular Ex. 00 00000-0000!");
        } else {
            try {
                bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
                String sql = "INSERT INTO pessoa (nome,email,celular) VALUES (?,?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, editTextNome3.getText().toString());
                stmt.bindString(2, editTextEmail3.getText().toString());
                stmt.bindString(3, editTextCelular3.getText().toString());
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