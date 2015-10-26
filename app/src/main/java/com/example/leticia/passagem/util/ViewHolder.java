package com.example.leticia.passagem.util;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by leticia on 25/10/2015.
 */
public class ViewHolder {
    private ImageView fotinhoPassagem;
    private TextView nomePassagem, detalhesPassagem;

    public ViewHolder(ImageView fotinhoPassagem, TextView nomePassagem, TextView detalhesPassagem) {
        this.fotinhoPassagem = fotinhoPassagem;
        this.nomePassagem = nomePassagem;
        this.detalhesPassagem = detalhesPassagem;
    }

    public ImageView getFotinhoPassagem() {
        return fotinhoPassagem;
    }

    public void setFotinhoPassagem(ImageView fotinhoPassagem) {
        this.fotinhoPassagem = fotinhoPassagem;
    }

    public TextView getNomePassagem() {
        return nomePassagem;
    }

    public void setNomePassagem(TextView nomePassagem) {
        this.nomePassagem = nomePassagem;
    }

    public TextView getDetalhesPassagem() {
        return detalhesPassagem;
    }

    public void setDetalhesPassagem(TextView detalhesPassagem) {
        this.detalhesPassagem = detalhesPassagem;
    }
}
