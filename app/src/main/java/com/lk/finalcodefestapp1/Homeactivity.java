package com.lk.finalcodefestapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.finalcodefestapp1.Model.Product;
import com.squareup.picasso.Picasso;

public class Homeactivity extends AppCompatActivity {



    Button news,ticket,logout;


    TextView fname,femail;

    RecyclerView recyclerView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageRef;
    private FirestoreRecyclerAdapter<Product, productHolder> recyclerAdapter;
    // private FirestoreRecyclerAdapter<Product,productHolder> recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);



        fname=findViewById(R.id.hname);
        femail=findViewById(R.id.hemail);


        news=findViewById(R.id.newsbtn);
        ticket=findViewById(R.id.ticketbtn);
        logout=findViewById(R.id.logoutbtn);

        recyclerView=findViewById(R.id.prdlist);

        storageRef=  FirebaseStorage.getInstance().getReference();
        Bundle bundle = getIntent().getExtras();

        String name = bundle.getString("name");
        String email = bundle.getString("email");
        String cusID = bundle.getString("cusID");


        Toast.makeText(this, ""+cusID, Toast.LENGTH_SHORT).show();


        fname.setText(name);
        femail.setText(email);


        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Homeactivity.this,Eventnews.class);
                intent.putExtra("cusID",cusID);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);



            }
        });




        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Homeactivity.this,tickets_form.class);
                intent.putExtra("cusID",cusID);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);


            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signOut();

            }
        });



        recyclerView.setLayoutManager(new GridLayoutManager(Homeactivity.this,2));




        Query loadjobs=db.collection("products").whereEqualTo("status","available");

        FirestoreRecyclerOptions recyclerOptions=new FirestoreRecyclerOptions.Builder<Product>().setQuery(loadjobs,Product.class).build();


        StorageReference reference = FirebaseStorage.getInstance().getReference();

        recyclerAdapter=new  FirestoreRecyclerAdapter<Product,productHolder>(recyclerOptions) {


            @Override
            protected void onBindViewHolder(@NonNull productHolder holder, int position, @NonNull Product model) {


                holder.pname.setText(""+model.getPrdname());
                holder.pprice.setText("Rs"+model.getPrice());
                holder.customer=fname.getText().toString();


                holder.productID=getSnapshots().getSnapshot(position).getId();



                reference.child("productimg/").child(model.getPrdimg()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.with(Homeactivity.this).load(uri).into(holder.prdimg);
                        // Got the download URL for 'users/me/profile.png'
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                       // Toast.makeText(rider_home.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }











            @NonNull
            @Override
            public productHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);

                return new productHolder(view);
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


    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent intent=new Intent(Homeactivity.this,MainActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);


                        finish();




                    }
                });
        // [END auth_fui_signout]
    }






}