package com.example.impressao3d;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ClienteActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public ListView listViewDados2;
    public ArrayList<Integer> arrayIds;
    public Integer idSelecionado;
    public Button btnCadastrarCliente, btnAtualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        listViewDados2 = (ListView) findViewById(R.id.listViewDados2);
        btnCadastrarCliente = (Button) findViewById(R.id.btnCadastrarCliente);
        btnAtualizar = (Button) findViewById(R.id.btnAtualizar);

        listarDados();

        btnCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarDados();
            }
        });

        listViewDados2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                confirmaExcluir();
                return true;
            }
        });

        listViewDados2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                abrirTelaEditar(arrayIds.get(i));
            }
        });

    }

    public void abrirTelaCadastro(){
        Intent intent1 = new Intent(this,CadastrarClienteActivity.class);
        startActivity(intent1);
    }

    public void abrirTelaEditar(Integer id){
        Intent intent2 = new Intent ( this, EditarClienteActivity.class);
        intent2.putExtra("id", id);
        startActivity(intent2);
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
            listViewDados2.setAdapter(meuAdapter);
            meuCursor.moveToFirst();
            while(meuCursor!=null){
                linhas.add("Nome: " + meuCursor.getString(1) + " | E-mail: " + meuCursor.getString(2) + " | Celular: " + meuCursor.getString(3));
                arrayIds.add(meuCursor.getInt(0));
                meuCursor.moveToNext();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void confirmaExcluir() {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(ClienteActivity.this);
        msgBox.setTitle("Excluir");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setMessage("Você realmente deseja excluir esse registro?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                excluir();
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        msgBox.show();
    }

    public void excluir(){
        //Toast.makeText(this, i.toString(), Toast.LENGTH_SHORT).show();
        try{
            bancoDados = openOrCreateDatabase("impressao3d", MODE_PRIVATE, null);
            String sql = "DELETE FROM pessoa WHERE id =?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindLong(1, idSelecionado);
            stmt.executeUpdateDelete();
            listarDados();
            bancoDados.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}