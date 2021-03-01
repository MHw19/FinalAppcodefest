package com.lk.finalcodefestapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lk.finalcodefestapp1.Model.Customer;
import com.squareup.picasso.Picasso;

import static android.widget.Toast.LENGTH_LONG;

public class Register_Activity extends AppCompatActivity {


    private static final int IMGREQUSET =102 ;
    EditText name,nic,mobile,email;

    ImageView imageView;

    Spinner gender;

    Button select,register;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String selectgender;
    private Uri customeruri;

    private StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);


        name=findViewById(R.id.namefield);
        nic=findViewById(R.id.nic);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.email);
        imageView=findViewById(R.id.profimg);

        gender=findViewById(R.id.gender);
        storageRef=  FirebaseStorage.getInstance().getReference();

        fillGender();

          select=findViewById(R.id.selectbtn);
          register=findViewById(R.id.registerbtn);


          select.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  Intent filechoser=new Intent();


                  filechoser.setAction(Intent.ACTION_GET_CONTENT);

                  filechoser.setType("image/*");

                  startActivityForResult(Intent.createChooser(filechoser,"select Driver photo"),IMGREQUSET);





              }
          });




        Bundle bundle = getIntent().getExtras();


        String rname = bundle.getString("name");
        String remail = bundle.getString("email");
        String rgoogleauth = bundle.getString("googleauth");



       name.setText(rname);
       email.setText(remail);


       gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               selectgender = parent.getSelectedItem().toString();

              // Toast.makeText(MainActivity.this,"Selected :"+selectedmonth,LENGTH_LONG).show();

             //  displaydate.setText("Year:"+selectedYear+"|"+"Month:"+selectedmonth);



           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });




       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String cname = name.getText().toString();
               String cnic = nic.getText().toString();
               String cemail = email.getText().toString();
               String cmobile =mobile.getText().toString();


               Customer customer=new Customer();


               customer.setName(cname);
               customer.setNic(cnic);
               customer.setEmail(cemail);
               customer.setMobile(cmobile);

               customer.setGender(selectgender);





              resisterpre(customer);




           }
       });








    }

    private void resisterpre(Customer customer) {

        String cusnames = customer.getName();

        String custmerimgpath="Cusimg"+cusnames+".png";



        storageRef.child("customersimgs/"+custmerimgpath).putFile(customeruri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(Register_Activity.this, "Upload sucessfully", Toast.LENGTH_SHORT).show();






            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //Uri uri = taskSnapshot.getUploadSessionUri();
                //String urisession = uri.toString();
                customer.setImgurl(custmerimgpath);
                resisteruser(customer);

            }
        });



    }

    private void resisteruser(Customer customer) {


        db.collection("customers")
                .add(customer)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {


                        Log.d("okk", "DocumentSnapshot added with ID: " + documentReference.getId());

                        Intent intent=new Intent(Register_Activity.this,Homeactivity.class);

                        intent.putExtra("email",customer.getEmail());
                        intent.putExtra("name",customer.getName());
                        //intent.putExtra("authid",authid);
                       // intent.putExtra("driverimg",rider.getDriverphotopath());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);

                        finish();



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //  Log.w(TAG, "Error adding document", e);
                    }
                });





    }

    private void fillGender() {

        ArrayAdapter<String> genderadapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.gender));

        genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        gender.setAdapter(genderadapter);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==IMGREQUSET){

            if(resultCode==RESULT_OK){

                customeruri =data.getData();

                Picasso.with(Register_Activity.this).load(customeruri).into(imageView);





            }

        }



    }





}