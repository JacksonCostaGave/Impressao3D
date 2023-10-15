package com.example.impressao3d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ClienteActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public ListView listViewDados;
    public ArrayList<Integer> arrayIds;
    public Integer idSelecionado;
    public Button btnCadastrarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        listViewDados = (ListView) findViewById(R.id.listViewDados2);
        btnCadastrarCliente = (Button) findViewById(R.id.btnCadastrarCliente);
        listarDados();
        btnCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });
    }

    public void abrirTelaCadastro(){
        Intent intent = new Intent(this,CadastrarClienteActivity.class);
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

    public void listarDados(){
        try{
            arrayIds = new ArrayList<>();
            bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome, email, celular FROM pessoa", null);
//            Cursor meuCursor2 = bancoDados.rawQuery("SELECT id, tipo, nome, cor, preco FROM produto", null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listViewDados.setAdapter(meuAdapter);
            meuCursor.moveToFirst();
            while(meuCursor!=null){
                arrayIds.add(meuCursor.getInt(0));
                linhas.add(meuCursor.getString(1));
                linhas.add(meuCursor.getString(2));
                linhas.add(meuCursor.getString(3));
                meuCursor.moveToNext();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}