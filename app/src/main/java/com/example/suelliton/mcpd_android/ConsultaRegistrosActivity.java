package com.example.suelliton.mcpd_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.suelliton.mcpd_android.adapters.RegistroAdapter;
import com.example.suelliton.mcpd_android.model.Registro;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class ConsultaRegistrosActivity extends AppCompatActivity{

    static String keyParaEditar;
    static Registro REGISTRO_PARA_EDITAR;

    final static int RESULT_EDIT = 1;
    final static int RESULT_EXIT = 2;
    Button btnConsulta;
    Button btn_Sair;
    Button  btn_Apontamentos;
    private FirebaseDatabase database ;
    private DatabaseReference registroReference ;
    private ChildEventListener childEventRegistro;
    private ValueEventListener childValueRegistro;
    List<Registro> listaRegistros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_registros);
        database =  FirebaseDatabase.getInstance();
        registroReference = database.getReference("Registro");
        listaRegistros = new ArrayList<>();
        final RegistroAdapter registroAdapter = new RegistroAdapter(this,listaRegistros);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_consulta_registros);
        recyclerView.setAdapter(registroAdapter);



       /* for (DataSnapshot snapshot:dataSnapshot.getChildren()) {

            Registro registro =  dataSnapshot.getValue(Registro.class);
            registro.setKey(snapshot.getKey());
            listaRegistros.add(registro);
            registroAdapter.notifyDataSetChanged();

        }*/


        childEventRegistro = registroReference.child("dataRegistro").orderByChild("dataRegistro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Registro registro =  dataSnapshot.getValue(Registro.class);
                registro.setKey(dataSnapshot.getKey());
                listaRegistros.add(registro);
                registroAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        registroReference.addChildEventListener(childEventRegistro);







        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(layout);

        recyclerView.addOnItemTouchListener(new MeuRecyclerViewClickListener(ConsultaRegistrosActivity.this, recyclerView, new MeuRecyclerViewClickListener.OnItemClickListener() {


            @Override
            public void OnItemClick(View view, int i) {
                Intent intent= new Intent();
                Bundle bundle = new Bundle();
                Registro registro = listaRegistros.get(i) ;
                keyParaEditar = registro.getKey();
                REGISTRO_PARA_EDITAR = registro;
                intent.putExtras(bundle);
                setResult(RESULT_EDIT,intent);
                finish();
            }

            @Override
            public void OnItemLongClick(View view, int i) {

            }

            @Override
            public void onItemClick(View view, int position) {

            }
        }));

        bindViews();
        setListeners();

    }

    public void bindViews(){
        btnConsulta = (Button) findViewById(R.id.btn_consultar);
        btn_Sair = (Button) findViewById(R.id.btn_sair);
        btn_Apontamentos = (Button) findViewById(R.id.btn_apontamentos);

    }

    public void setListeners() {
        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultaRegistrosActivity.this,ConsultaRegistrosActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_Apontamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultaRegistrosActivity.this,RegistroActivity.class);
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
        btn_Sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_EXIT,intent);
                finish();
            }
        });


    }
    public void preencheRegistros(){

        Registro r1 = new Registro("20/11-11:20:30",127,321,4,false,"20/11-11:20:30","Corte","tudo ok",1,2,3);
        Registro r2 = new Registro("20/11-11:20:30",347,651,2,true,"20/11-11:20:30","Poda","de boa",1,2,3);
        Registro r3 = new Registro("20/11-11:20:30",127,321,4,false,"20/11-11:20:30","Remoção","tudo ok",1,2,3);
        Registro r4 = new Registro("20/11-11:20:30",347,651,2,true,"20/11-11:20:30","Aplicação inseticida","de boa",1,2,3);
        Registro r5 = new Registro("20/11-11:20:30",127,321,4,false,"20/11-11:20:30","Remoção","tudo ok",1,2,3);
        Registro r6 = new Registro("20/11-11:20:30",347,651,2,true,"20/11-11:20:30","Corte","de boa",1,2,3);
        Registro r7 = new Registro("20/11-11:20:30",127,321,4,false,"20/11-11:20:30","Corte","tudo ok",1,2,3);
        Registro r8 = new Registro("20/11-11:20:30",347,651,2,true,"20/11-11:20:30","Poda","de boa",1,2,3);
        Registro r9 = new Registro("20/11-11:20:30",127,321,4,false,"20/11-11:20:30","Remoção","tudo ok",1,2,3);
        Registro r10 = new Registro("20/11-11:20:30",347,651,2,true,"20/11-11:20:30","Aplicação inseticida","de boa",1,2,3);
        Registro r11 = new Registro("20/11-11:20:30",127,321,4,false,"20/11-11:20:30","Remoção","tudo ok",1,2,3);
        Registro r12 = new Registro("20/11-11:20:30",347,651,2,true,"20/11-11:20:30","Corte","de boa",1,2,3);
        listaRegistros.add(r1);
        listaRegistros.add(r2);
        listaRegistros.add(r3);
        listaRegistros.add(r4);
        listaRegistros.add(r5);
        listaRegistros.add(r6);
        listaRegistros.add(r7);
        listaRegistros.add(r8);
        listaRegistros.add(r9);
        listaRegistros.add(r10);
        listaRegistros.add(r11);
        listaRegistros.add(r12);
    }



}
