package com.example.impressao3d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Usuario_Login_Activity extends AppCompatActivity {
    SQLiteDatabase bancoDados;
    EditText editTextLogin,editTextSenha;
    Button buttonEntrar;
    TextView textViewCadastrarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_login);

        criarBancoDados();

        editTextLogin = (EditText) findViewById(R.id.editTextNome);
        editTextSenha = (EditText) findViewById(R.id.editTextEmail);
        buttonEntrar = (Button) findViewById(R.id.buttonEntrar);
        textViewCadastrarLogin = (TextView) findViewById(R.id.textViewCadastrarLogin);

        textViewCadastrarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastro();
            }
        });

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar();
            }
        });
    }

    public void criarBancoDados(){
        try {
            bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS usuario(" +
                    "   user VARCHAR PRIMARY KEY" +
                    " , senha VARCHAR NOT NULL" +
                    " ) " );
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirCadastro(){
        Intent intent = new Intent(this, Usuario_Cadastro_Activity.class);
        startActivity(intent);
    }

    public void entrar(){
        bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
        String user = editTextLogin.getText().toString();
        String senha = editTextSenha.getText().toString();
        Cursor cursor = bancoDados.rawQuery("SELECT user FROM usuario WHERE user = '"+user+"' AND senha = '"+senha+"'", null);
        if(cursor.moveToFirst()){
            SharedPreferences impressao3d = getSharedPreferences("impressao3d", MODE_PRIVATE);
            SharedPreferences.Editor editor = impressao3d.edit();
            editor.putString("user", user);
            editor.commit();
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Usuário ou senha inexistente!", Toast.LENGTH_SHORT).show();
        }
    }
}