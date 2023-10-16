package com.example.impressao3d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Produto_Editar_Activity extends AppCompatActivity {
    Integer id;
    EditText editTextNome5, editTextCor5,editTextValor5;
    Button buttonEditarProduto, btnRetornar5;
    SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_editar);

        buttonEditarProduto = (Button) findViewById(R.id.buttonEditarProduto);
        btnRetornar5 = (Button) findViewById(R.id.btnRetornar5);
        editTextNome5 = (EditText) findViewById(R.id.editTextNome5);
        editTextCor5 = (EditText) findViewById(R.id.editTextCor5);
        editTextValor5 = (EditText) findViewById(R.id.editTextValor5);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        carregarDados();

        buttonEditarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar();
                carregarDados();
            }
        });

        btnRetornar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void carregarDados(){
        try {
            bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id,nome,cor,valor FROM produto WHERE id = " + id.toString(), null);
            meuCursor.moveToFirst();
            editTextNome5.setText(meuCursor.getString(1));
            editTextCor5.setText(meuCursor.getString(2));
            editTextValor5.setText(meuCursor.getString(3));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void editar(){
        try{
            bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
            String sql = "UPDATE produto SET nome=?, cor=?, Valor=? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1,editTextNome5.getText().toString());
            stmt.bindString(2, editTextCor5.getText().toString());
            stmt.bindLong(3,Integer.parseInt(editTextValor5.getText().toString()));
            stmt.bindLong(4,id);
            stmt.executeUpdateDelete();
            bancoDados.close();
            Toast.makeText(this, "Produto editado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}