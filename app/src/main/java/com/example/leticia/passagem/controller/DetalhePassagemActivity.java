package com.example.leticia.passagem.controller;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import com.example.leticia.passagem.R;
import com.example.leticia.passagem.model.Passagem;
import com.example.leticia.passagem.network.PassagemRequester;


/**
 * Created by leticia on 25/10/2015.
 */
public class DetalhePassagemActivity  extends ActionBarActivity{

    TextView passagemNome;
    ImageView passagemImageView;
    TextView passagemPreco;
    TextView passagemOrigem;
    TextView passagemDestino;
    PassagemRequester requester;
    ProgressBar mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_passagem);

        Intent intent = getIntent();
        final Passagem passagem = (Passagem)intent.getSerializableExtra(ListaPassagemActivity.PASSAGEM);
        setupViews(passagem);

        requester = new PassagemRequester();
        if(requester.isConnected(this)) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mProgress.setVisibility(View.VISIBLE);
                        final Bitmap img = requester.getImage(passagem.getImagem());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                passagemImageView.setImageBitmap(img);
                                mProgress.setVisibility(View.INVISIBLE);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.sem_passagem);
            passagemImageView.setImageDrawable(drawable);
            Toast toast = Toast.makeText(this, "Rede indisponível!", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    private void setupViews(Passagem passagem) {
        passagemNome = (TextView) findViewById(R.id.txt_passagem_nome);
        passagemNome.setText(passagem.getNome());
        passagemImageView = (ImageView) findViewById(R.id.passagem_image_view);
        passagemPreco = (TextView) findViewById(R.id.txt_passagem_preco);
        Locale locale = new Locale("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        passagemPreco.setText(""+formatter.format(passagem.getPreco()));
        passagemOrigem = (TextView) findViewById(R.id.txt_passagem_origem);
        passagemOrigem.setText(passagem.getOrigem());
        passagemDestino = (TextView) findViewById(R.id.txt_passagem_destino);
        passagemDestino.setText(passagem.getDestino());

        mProgress = (ProgressBar) findViewById(R.id.carregando_passagem);
        mProgress.setVisibility(View.INVISIBLE);
    }

}
