package com.example.katiuskagonzalez.finalaa;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.katiuskagonzalez.finalaa.models.agregar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Recyclyer_activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;


    private RecyclerView.LayoutManager Manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclyer_activity);

        final List items = new ArrayList();


        mRecyclerView = findViewById(R.id.recycler_view);

        Manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(Manager);

        mAdapter = new mAdapterView(items, this);
        mRecyclerView.setAdapter(mAdapter);


            FirebaseDatabase.getInstance().getReference("Historia").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        agregar a = dataSnapshot1.getValue(agregar.class);
                        items.add(a);
                    }

                    new mAdapterView(items, Recyclyer_activity.this);

                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Recyclyer_activity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

        }
    }


