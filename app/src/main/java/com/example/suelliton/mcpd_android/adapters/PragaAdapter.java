package com.example.suelliton.mcpd_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.suelliton.mcpd_android.R;
import com.example.suelliton.mcpd_android.model.Praga;

import java.util.List;

/**
 * Created by suelliton on 12/11/2017.
 */

public class PragaAdapter extends BaseAdapter {
    Context context;
    List<Praga> listaPragas;
    public PragaAdapter(Context context, List<Praga> objects) {
        super();
        this.context = context;
        this.listaPragas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.praga_adapter_inflate, parent, false);
            holder.pragaTextView = (TextView) convertView.findViewById(R.id.praga_text);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }


        Praga praga = (Praga) getItem(position);

        holder.pragaTextView.setText(praga.getNome());
        holder.pragaTextView.setTextSize(25);
        return convertView;
    }

    @Override
    public int getCount() {
        return listaPragas.size();
    }

    @Override
    public Object getItem(int i) {
        return listaPragas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaPragas.get(i).getId();
    }

    class ViewHolder{
        TextView pragaTextView;
    }



}
