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

public class Cliente_Editar_Activity extends AppCompatActivity {
    Integer id;
    EditText editTextNome2, editTextEmail2,editTextCelular2;
    Button buttonEditarCliente, btnRetornar;
    SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_editar);

        buttonEditarCliente = (Button) findViewById(R.id.buttonEditarCliente);
        btnRetornar = (Button) findViewById(R.id.btnRetornar);
        editTextNome2 = (EditText) findViewById(R.id.editTextNome2);
        editTextEmail2 = (EditText) findViewById(R.id.editTextEmail2);
        editTextCelular2 = (EditText) findViewById(R.id.editTextCelular2);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        carregarDados();

        buttonEditarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar();
                carregarDados();
            }
        });

        btnRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void carregarDados(){
        try {
            bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id,nome,email,celular FROM pessoa WHERE id = " + id.toString(), null);
            meuCursor.moveToFirst();
            editTextNome2.setText(meuCursor.getString(1));
            editTextEmail2.setText(meuCursor.getString(2));
            editTextCelular2.setText(meuCursor.getString(3));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void editar(){
        try{
            bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
            String sql = "UPDATE pessoa SET nome=?, email=?, celular=? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1,editTextNome2.getText().toString());
            stmt.bindString(2, editTextEmail2.getText().toString());
            stmt.bindString(3,editTextCelular2.getText().toString());
            stmt.bindLong(4,id);
            stmt.executeUpdateDelete();
            bancoDados.close();
            Toast.makeText(this, "Usuário editado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}