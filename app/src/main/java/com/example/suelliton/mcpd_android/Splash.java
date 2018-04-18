package com.example.suelliton.mcpd_android;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.suelliton.mcpd_android.model.Cultura;
import com.example.suelliton.mcpd_android.model.Praga;
import com.example.suelliton.mcpd_android.model.Registro;
import com.example.suelliton.mcpd_android.model.Usuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Splash extends AppCompatActivity {

    private FirebaseDatabase database ;
    private DatabaseReference culturaReference ;
    private DatabaseReference pragaReference;
    private DatabaseReference usuarioReference;
    private DatabaseReference registroReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        database = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);//persistencia em disco local
        culturaReference = database.getReference().child("Cultura");
        pragaReference = database.getReference().child("Praga");
        usuarioReference = database.getReference().child("Usuario");
        registroReference = database.getReference().child("Registro");

        //preencheBanco();
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarLogin();
            }
        }, 800);
    }

    public void preencheBanco(){
        preencheCulturas();
        preenchePragas();
        preencheUsuarios();
        preencheRegistros();
        exibeDados();
    }
    public void preencheCulturas(){
        Cultura c1 = new Cultura(0,"Cacau","é um cacaueiro");
        Cultura c2 = new Cultura(1,"Banana","é um babanal");
        Cultura c3 = new Cultura(2,"Mamão","é um mamoeiro");
        culturaReference.push().setValue(c1);
        culturaReference.push().setValue(c2);
        culturaReference.push().setValue(c3);
    }

    public void preenchePragas(){
        Praga p1 = new Praga(0,"Pulgões","1","2","3","4","5",0);
        Praga p2 = new Praga(1,"Colchonilhas","1","2","3","4","5",0);
        Praga p3 = new Praga(2,"Ácaros","1","2","3","4","5",0);

        Praga p4 = new Praga(3,"Largatas","1","2","3","4","5",1);
        Praga p5 = new Praga(4,"Nematóides","1","2","3","4","5",1);
        Praga p6 = new Praga(5,"Bemicia tabasi","1","2","3","4","5",1);

        Praga p7 = new Praga(6,"Mosca Branca","1","2","3","4","5",2);
        Praga p8 = new Praga(7,"Schistocerca gregaria","1","2","3","4","5",2);
        Praga p9 = new Praga(8,"Locusta migratoria","1","2","3","4","5",2);

        pragaReference.push().setValue(p1);
        pragaReference.push().setValue(p2);
        pragaReference.push().setValue(p3);
        pragaReference.push().setValue(p4);
        pragaReference.push().setValue(p5);
        pragaReference.push().setValue(p6);
        pragaReference.push().setValue(p7);
        pragaReference.push().setValue(p8);
        pragaReference.push().setValue(p9);

    }
    public void preencheUsuarios(){
        Usuario u1 = new Usuario(0,"suelliton","12345678912");
        Usuario u2 = new Usuario(0,"washington","02402402469");
        usuarioReference.push().setValue(u1);
        usuarioReference.push().setValue(u2);
    }
    public void preencheRegistros(){
        Registro r1 = new Registro("11-11-2017",129,343,4,false,"","","Não houve tratamento",0,0,0);
        Registro r2 = new Registro("12-11-2017",234,267,4,true,"","Inseticida","houve tratamento",0,0,0);
        registroReference.push().setValue(r1);
        registroReference.push().setValue(r2);
    }
    public void exibeDados(){
        culturaReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Cultura c = dataSnapshot.getValue(Cultura.class);
                Log.i("firebase",c.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void mostrarLogin(){
        Intent intent = new Intent(Splash.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
