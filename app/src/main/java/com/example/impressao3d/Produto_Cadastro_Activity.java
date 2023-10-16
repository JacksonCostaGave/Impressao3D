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

public class Produto_Cadastro_Activity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    Button buttonCadastrarProduto4, btnRetornar4;
    EditText editTextNome4, editTextCor4, editTextValor4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_cadastro);

        buttonCadastrarProduto4 = (Button) findViewById(R.id.buttonCadastrarProduto4);
        btnRetornar4 = (Button) findViewById(R.id.btnRetornar4);
        editTextNome4 = (EditText) findViewById(R.id.editTextNome4);
        editTextCor4 = (EditText) findViewById(R.id.editTextCor4);
        editTextValor4 = (EditText) findViewById(R.id.editTextValor4);

        btnRetornar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonCadastrarProduto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    public void cadastrar(){
        if(TextUtils.isEmpty(editTextNome4.getText().toString())){
            editTextNome4.setError("Digite um Nome!");
        } else if(TextUtils.isEmpty(editTextCor4.getText().toString())){
            editTextCor4.setError("Digite o nome de uma Cor");
        } else if(TextUtils.isEmpty(editTextValor4.getText().toString())){
            editTextValor4.setError("Digite um Valor!");
        } else {
            try {
                bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
                String sql = "INSERT INTO produto (nome,cor,valor) VALUES (?,?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, editTextNome4.getText().toString());
                stmt.bindString(2, editTextCor4.getText().toString());
                stmt.bindLong(3, Integer.parseInt(editTextValor4.getText().toString()));
                stmt.executeInsert();
                bancoDados.close();
                finish();
                Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}