package com.example.tads.mcpd_android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tads.mcpd_android.R;
import com.example.tads.mcpd_android.adapters.RegistroAdapter;
import com.example.tads.mcpd_android.model.Registro;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RegistroFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private FirebaseDatabase database;
    private DatabaseReference registroReference;
    private ChildEventListener childEventRegistro;
    private ValueEventListener childValueRegistro;
    private TextView textView;
    List<Registro> listaRegistros;

    RecyclerView recyclerView = null;
    RegistroAdapter registroAdapter = null;

    public RegistroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        database = FirebaseDatabase.getInstance();
        registroReference = database.getReference("Registro");
        listaRegistros = new ArrayList<>();
        registroAdapter = new RegistroAdapter(this.getContext(), listaRegistros);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(registroAdapter);

        childEventRegistro = registroReference.child("dataRegistro").orderByChild("dataRegistro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Registro registro = dataSnapshot.getValue(Registro.class);
                registro.setKey(dataSnapshot.getKey());
                listaRegistros.add(registro);
                registroAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                registroAdapter.notifyDataSetChanged();
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

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        // Inflate the layout for this fragment
        return view;
    }

}
