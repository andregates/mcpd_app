package com.example.suelliton.mcpd_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.suelliton.mcpd_android.R;
import com.example.suelliton.mcpd_android.RegistroActivity;
import com.example.suelliton.mcpd_android.model.Propriedade;

import java.util.List;

/**
 * Created by Aluno on 23/08/2017.
 */

public class PropriedadeAdapter extends RecyclerView.Adapter{
    List<Propriedade> listaPropriedade;
    Context context;
    private PropriedadeHolder last_item_clicked;
    Intent intent;

    public PropriedadeAdapter(List<Propriedade> listaPropriedade, Context context) {
        this.listaPropriedade = listaPropriedade;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inflate_propriedade,parent,false);
        PropriedadeHolder holder = new PropriedadeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final PropriedadeHolder holder = (PropriedadeHolder) viewHolder;
        final Propriedade propriedade = listaPropriedade.get(position);

        holder.getmProp_nome().setText(propriedade.getNome());
        holder.getmProp_localidade().setText(propriedade.getLocalidade());
        holder.getmProp_cidade().setText(propriedade.getCidade());

        holder.getmRow().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(last_item_clicked!=null)
                    last_item_clicked.getmRow().setBackgroundColor(Color.parseColor("#ffffff"));
                holder.getmRow().setBackground(v.getResources().getDrawable(R.drawable.shape_button));
                last_item_clicked=holder;
                intent = new Intent(context,RegistroActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putLong("idPropriedade", propriedade.getId());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPropriedade.size();
    }

    public static class PropriedadeHolder extends RecyclerView.ViewHolder{
        private TextView mProp_nome;
        private TextView mProp_localidade;
        private TextView mProp_cidade;
        private RelativeLayout mRow;

        public PropriedadeHolder(View view){
            super(view);
            mProp_nome = (TextView) view.findViewById(R.id.prop_nome);
            mProp_localidade = (TextView) view.findViewById(R.id.prop_localidade);
            mProp_cidade = (TextView) view.findViewById(R.id.prop_cidade);
            mRow = (RelativeLayout) view.findViewById(R.id.row_propriedade);
        }

        public TextView getmProp_nome() {
            return mProp_nome;
        }

        public void setmProp_nome(TextView mProp_nome) {
            this.mProp_nome = mProp_nome;
        }

        public TextView getmProp_localidade() {
            return mProp_localidade;
        }

        public void setmProp_localidade(TextView mProp_localidade) {
            this.mProp_localidade = mProp_localidade;
        }

        public TextView getmProp_cidade() {
            return mProp_cidade;
        }

        public void setmProp_cidade(TextView mProp_cidade) {
            this.mProp_cidade = mProp_cidade;
        }

        public RelativeLayout getmRow() {
            return mRow;
        }

        public void setmRow(RelativeLayout mRow) {
            this.mRow = mRow;
        }
    }
}
