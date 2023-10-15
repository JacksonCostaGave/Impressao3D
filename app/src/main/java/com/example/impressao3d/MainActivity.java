package com.example.impressao3d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    private TextView textViewHello;
    private Button btnCliente, btnProduto;
    private ArrayList<Integer> arrayIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        criarBancoDados();
//        inserirDadosTemp();

        SharedPreferences impressao3d = getSharedPreferences("impressao3d", MODE_PRIVATE);

        btnCliente = (Button) findViewById(R.id.btnCliente);
        btnProduto = (Button) findViewById(R.id.btnProduto);

        btnCliente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                abrirTelaCliente();
            }
        });

        btnProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaProduto();
            }
        });

        textViewHello = (TextView) findViewById(R.id.textViewHello);

        String user = impressao3d.getString("user","");
        textViewHello.setText("Olá "+user+"!");
    }

    public void criarBancoDados(){
        try{
            bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoa(" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", nome VARCHAR" +
                    ", email VARCHAR" +
                    ", celular VARCHAR)");
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS produto(" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", tipo VARCHAR" +
                    ", nome VARCHAR" +
                    ", cor VARCHAR" +
                    ", preco FLOAT)");
            bancoDados.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void inserirDadosTemp(){
        try{
            bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
            String sql = "INSERT INTO pessoa (nome,email,celular) VALUES (?,?,?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1,"Jackson da Costa Gave");
            stmt.bindString(2,"jackson.gave@aluno.ifsp.edu.br");
            stmt.bindString(3,"19 99530-7812");
            stmt.executeInsert();

            stmt.bindString(1,"Marcos da Silva");
            stmt.bindString(2,"marcos@gmail.com");
            stmt.bindString(3,"11 98758-8785");
            stmt.executeInsert();

            stmt.bindString(1,"Taís Floriano");
            stmt.bindString(2,"taisf@gmail.com");
            stmt.bindString(3,"17 98957-1256");
            stmt.executeInsert();

            String sql2 = "INSERT INTO produto (tipo,nome,cor,preco) VALUES (?,?,?,?)";
            SQLiteStatement stmt2 = bancoDados.compileStatement(sql2);

            stmt2.bindString(1,"Filamento");
            stmt2.bindString(2,"ABS");
            stmt2.bindString(3,"Preto");
            stmt2.bindLong(4,100);
            stmt2.executeInsert();

            stmt2.bindString(1,"Filamento");
            stmt2.bindString(2,"PLA");
            stmt2.bindString(3,"Branco");
            stmt2.bindLong(4,120);
            stmt2.executeInsert();

            stmt2.bindString(1,"Impressora 3D");
            stmt2.bindString(2,"Creality Ender 3 S1");
            stmt2.bindString(3,"Preto");
            stmt2.bindLong(4,3500);
            stmt2.executeInsert();

            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void abrirTelaCliente(){
        Intent intent = new Intent(this, ClienteActivity.class);
        startActivity(intent);
    }

    public void abrirTelaProduto(){
        Intent intent = new Intent(this, ProdutoActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sair:
                sair();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sair(){
        SharedPreferences impressao3d = getSharedPreferences("impressao3d", MODE_PRIVATE);
        SharedPreferences.Editor editor = impressao3d.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}