package com.example.leticia.passagem.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.leticia.passagem.model.Passagem;
import com.example.leticia.passagem.network.PassagemRequester;
import com.example.leticia.passagem.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by leticia on 25/10/2015.
 */
public class MainActivity extends ActionBarActivity {

    Spinner spinnerOrigem;
    Spinner spinnerDestino;
    Button btnConsultar;
    String  destino, origem;
    ArrayList<Passagem> passagens;
    final String servidor = "jbossews-passagem.rhcloud.com";
    //final String servidor = "10.0.2.2:8080";
    PassagemRequester requester;
    ProgressBar mProgress;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        origem = "";
        destino = "";

        btnConsultar = (Button) findViewById(R.id.botao_enviar);
        spinnerOrigem = (Spinner) findViewById(R.id.dropdown_origens);
        spinnerOrigem.setOnItemSelectedListener(new OrigemSelecionado());
        spinnerDestino = (Spinner) findViewById(R.id.dropdown_destinos);
        spinnerDestino.setOnItemSelectedListener(new DestinoSelecionado());
        mProgress = (ProgressBar) findViewById(R.id.carregando);
        mProgress.setVisibility(View.INVISIBLE);

    }

    private class OrigemSelecionado implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            origem = (String) parent.getItemAtPosition(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class DestinoSelecionado implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            destino = (String) parent.getItemAtPosition(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }


    }

        // constante static para identificar o parametro
        public final static String PASSAGENS = "com.example.leticia.passagem";
        //será chamado quando o usuário clicar em enviar
        public void consultarCervejas(View view) {
            final String pOrigem = this.origem.equals("Escolha a origem")?"":origem;
            final String pDestino = this.destino.equals("Escolha o destino")?"":destino;

            requester = new PassagemRequester();
            if(requester.isConnected(this)) {
                intent = new Intent(this, ListaPassagemActivity.class);

                mProgress.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            passagens = requester.get("http://" + servidor + "/selecao.json", pOrigem, pDestino);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    intent.putExtra(PASSAGENS, passagens);
                                    mProgress.setVisibility(View.INVISIBLE);
                                    startActivity(intent);
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                Toast toast = Toast.makeText(this, "Rede indisponível!", Toast.LENGTH_LONG);
                toast.show();
            }
        }

}
