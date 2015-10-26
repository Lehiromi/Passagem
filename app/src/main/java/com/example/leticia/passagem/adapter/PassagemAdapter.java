package com.example.leticia.passagem.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.leticia.passagem.model.Passagem;
import com.example.leticia.passagem.R;
import com.example.leticia.passagem.util.Util;
import com.example.leticia.passagem.util.ViewHolder;
import com.example.leticia.passagem.adapter.SectionIndexBuider;

import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Locale;

/**
 * Created by leticia on 25/10/2015.
 */
public class PassagemAdapter extends BaseAdapter implements SectionIndexer {

    Activity context;
    Passagem[] passagens;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;

    public PassagemAdapter(Activity context, Passagem[] passagens){
        this.context = context;
        this.passagens = passagens;
        sectionHeaders = SectionIndexBuider.BuildSectionHeaders(passagens);
        positionForSectionMap = SectionIndexBuider.BuildPositionForSectionMap(passagens);
        sectionForPositionMap = SectionIndexBuider.BuildSectionForPositionMap(passagens);

    }

    @Override
    public int getCount() {
        return passagens.length;
    }

    @Override
    public Object getItem(int position) {
        if(position >= 0 && position < passagens.length)
            return passagens[position];
        else
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //o list view recicla os layouts para melhor performance
        //o layout reciclado vem no parametro convert view
        View view = convertView;
        //se nao recebeu um layout para reutilizar deve inflar um
        if(view == null) {
            //um inflater transforma um layout em uma view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_passagem, parent, false);

            ImageView fotinhoPassagem = (ImageView)view.findViewById(R.id.fotinhoPassageImageView);
            TextView nomePassagem = (TextView)view.findViewById(R.id.nomePassagemTextView);
            TextView detalhesPassagem = (TextView)view.findViewById(R.id.detalhesPassagemTextView);
            //faz cache dos widgets instanciados na tag da view para reusar quando houver reciclagem
            view.setTag(new ViewHolder(fotinhoPassagem, nomePassagem, detalhesPassagem));
        }
        //usa os widgets cacheados na view reciclada
        ViewHolder holder = (ViewHolder)view.getTag();
        //carrega os novos valores
        Drawable drawable = Util.getDrawable(context, passagens[position].getDestino());
        holder.getFotinhoPassagem().setImageDrawable(drawable);
        Locale locale = new Locale("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        holder.getNomePassagem().setText(passagens[position].getNome());
        holder.getDetalhesPassagem().setText(String.format("%s - %s", passagens[position].getOrigem(),
                formatter.format(passagens[position].getPreco())));

        return view;
    }
    @Override
    public Object[] getSections() {
        return sectionHeaders;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return positionForSectionMap.get(sectionIndex).intValue();
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionForPositionMap.get(position).intValue();
    }
}
