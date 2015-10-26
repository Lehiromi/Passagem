package com.example.leticia.passagem.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.leticia.passagem.adapter.PassagemAdapter;
import com.example.leticia.passagem.R;
import com.example.leticia.passagem.model.Passagem;

import java.util.ArrayList;

/**
 * Created by leticia on 25/10/2015.
 */
public class ListaPassagemActivity extends ActionBarActivity {
    ListView listView;
    Activity atividade;
    public final static String PASSAGEM = "com.example.leticia.passagem";
    Passagem[] passagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_passagem);
        atividade = this;
        //pega a mensagem do intent
        Intent intent = getIntent();
        passagens = ((ArrayList<Passagem>)intent.getSerializableExtra(MainActivity.PASSAGENS)).toArray(new Passagem[0]);

        //cria o listview de passagens
        listView = (ListView) findViewById(R.id.view_lista_passagem);

        PassagemAdapter adapter = new PassagemAdapter(this, passagens);

        listView.setAdapter(adapter);

        // listener de click em um item do listview

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // manda para a tela de detalhe
                Intent intent = new Intent(atividade, DetalhePassagemActivity.class);
                intent.putExtra(PASSAGEM, passagens[position]);

                startActivity(intent);

            }

        });



    }
}
