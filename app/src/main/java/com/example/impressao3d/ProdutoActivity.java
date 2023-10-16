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

public class ProdutoActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public ListView listViewDados2;
    public ArrayList<Integer> arrayIds;
    public Integer idSelecionado;
    public Button btnCadastrarProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        listViewDados2 = (ListView) findViewById(R.id.listViewDados2);
        btnCadastrarProduto = (Button) findViewById(R.id.btnCadastrarProduto);
        listarDados();
        btnCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                abrirTelaCadastro();
            }
        });
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
            Cursor meuCursor2 = bancoDados.rawQuery("SELECT id, nome, cor, valor FROM produto", null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listViewDados2.setAdapter(meuAdapter);
            meuCursor2.moveToFirst();
            while(meuCursor2!=null){
                arrayIds.add(meuCursor2.getInt(0));
                linhas.add("Nome: " + meuCursor2.getString(1) + " | " + "Cor: " + meuCursor2.getString(2) + " | " + "Valor: " + meuCursor2.getString(3));
                meuCursor2.moveToNext();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}