package com.example.suelliton.mcpd_android;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suelliton.mcpd_android.adapters.CulturaAdapter;
import com.example.suelliton.mcpd_android.model.Cultura;
import com.example.suelliton.mcpd_android.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Button mBtn_login;
    EditText ed_login;
    EditText ed_password;
    List<Usuario> listaUsuarios;
    private FirebaseDatabase database ;
    private DatabaseReference usuarioReference ;
    private ValueEventListener childValueUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = FirebaseDatabase.getInstance();
        usuarioReference = database.getReference("Usuario");
        listaUsuarios = new ArrayList<>();



        childValueUsuario = usuarioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaUsuarios.removeAll(listaUsuarios);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = snapshot.getValue(Usuario.class);//pega o objeto do firebase
                    listaUsuarios.add(usuario);//adiciona na lista que vai para o adapter

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        usuarioReference.addValueEventListener(childValueUsuario);

        bindViews();
        setListeners();
    }

    private void bindViews() {
        mBtn_login = (Button) findViewById(R.id.btn_login);
        ed_login = (EditText) findViewById(R.id.ed_login);
        ed_password = (EditText) findViewById(R.id.ed_password);
    }

    private void setListeners() {









        mBtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String login = ed_login.getText().toString();
                String password = ed_password.getText().toString();





                for (Usuario usuario: listaUsuarios) {
                    Log.i("usuarios",usuario.getNomeUsuario());
                    if(login.equals(usuario.getNomeUsuario()) && password.equals(usuario.getCpf())){
                        Intent intent = new Intent(LoginActivity.this,RegistroActivity.class );;
                        Bundle bundle = new Bundle();
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }


                Snackbar.make((View)v.getParent(), "Usuário ou Senha inválidos", Snackbar.LENGTH_SHORT).show();







            }
        });
    }




}
