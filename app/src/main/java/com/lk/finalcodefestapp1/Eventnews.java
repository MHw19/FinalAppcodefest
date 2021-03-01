package com.lk.finalcodefestapp1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.finalcodefestapp1.Model.News;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Eventnews extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter<News,NewsHolder> recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventnews);

        recyclerView=findViewById(R.id.eventlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Query loadjobs = db.collection("News");



        FirestoreRecyclerOptions recyclerOptions = new FirestoreRecyclerOptions.Builder<News>().setQuery(loadjobs, News.class).build();


        StorageReference reference = FirebaseStorage.getInstance().getReference();

        recyclerAdapter = new FirestoreRecyclerAdapter<News, NewsHolder>(recyclerOptions) {


            @Override
            protected void onBindViewHolder(@NonNull NewsHolder holder, int position, @NonNull News model) {


                holder.etitle.setText("" + model.getNtitle());
                holder.edes.setText("Rs" + model.getNdescription());
                holder.eloc.setText("" + model.getNlocation());

                holder.newsID=getSnapshots().getSnapshot(position).getId();





            }


            @NonNull
            @Override
            public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventitem, parent, false);

                return new NewsHolder(view);
            }
        };


        recyclerView.setAdapter(recyclerAdapter);










    }





    @Override
    protected void onStart() {
        super.onStart();

        recyclerAdapter.startListening();

    }


    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();


    }





}