package com.example.tads.mcpd_android.DAO;

import android.util.Log;

import com.example.tads.mcpd_android.model.Cultura;
import com.example.tads.mcpd_android.model.Praga;
import com.example.tads.mcpd_android.model.Registro;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegistroDAOImpl {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public ArrayList<Cultura> findCulturas() {

        final ArrayList<Cultura> culturas = new ArrayList<>();
        ChildEventListener childEventCult = null;
        final DatabaseReference culturaRef = database.getReference().child("Cultura");

        childEventCult = culturaRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cultura cultura = dataSnapshot.getValue(Cultura.class);
                cultura.setKey(dataSnapshot.getKey());
                culturas.add(cultura);
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
        culturaRef.addChildEventListener(childEventCult);


        return culturas;
    }

    public ArrayList<Praga> findPragas() {

        final ArrayList<Praga> pragas = new ArrayList<>();
        ChildEventListener childEventPraga = null;
        final DatabaseReference pragaRef = database.getReference().child("Praga");

        childEventPraga = pragaRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Praga cultura = dataSnapshot.getValue(Praga.class);
                pragas.add(cultura);
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
        pragaRef.addChildEventListener(childEventPraga);


        return pragas;
    }
}
