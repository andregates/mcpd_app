package com.example.suelliton.mcpd_android.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.suelliton.mcpd_android.R;
import com.example.suelliton.mcpd_android.model.Cultura;

import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by suelliton on 11/11/2017.
 *
 */

public class CulturaAdapter extends BaseAdapter {
    Context context;
    List<Cultura> listaCulturas;
    public CulturaAdapter(Context context, List<Cultura> objects) {
        super();
        this.context = context;
        this.listaCulturas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.cultura_adapter_inflate, parent, false);
            holder.culturaTextView = (TextView) convertView.findViewById(R.id.cultura_text);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }


        Cultura cultura = (Cultura) getItem(position);

        holder.culturaTextView.setText(cultura.getNome());
        holder.culturaTextView.setTextSize(25);
        return convertView;
    }

    @Override
    public int getCount() {
        return listaCulturas.size();
    }

    @Override
    public Object getItem(int i) {
        return listaCulturas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaCulturas.get(i).getId();
    }

    class ViewHolder{
        TextView culturaTextView;
    }

}


