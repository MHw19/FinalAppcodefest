package com.lk.finalcodefestapp1;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lk.finalcodefestapp1.Model.Ticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;




public class tickets_form extends AppCompatActivity {


    EditText title,description;
    Spinner option;
    Button submit;
    public String selectedoption;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String cusID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_form);


        title=findViewById(R.id.tickettitle);
        description=findViewById(R.id.ticketdes);
        option=findViewById(R.id.options);

        fillOptions();
        submit=findViewById(R.id.submitbtn);


        Bundle bundle = getIntent().getExtras();

      cusID = bundle.getString("cusID");


        Toast.makeText(this, ""+cusID, Toast.LENGTH_SHORT).show();


        option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedoption = parent.getSelectedItem().toString();

                // Toast.makeText(MainActivity.this,"Selected :"+selectedmonth,LENGTH_LONG).show();

                //  displaydate.setText("Year:"+selectedYear+"|"+"Month:"+selectedmonth);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Ticket ticket=new Ticket();

                 ticket.setTitle(title.getText().toString());
                 ticket.setDescription(description.getText().toString());

                 ticket.setOption(selectedoption);

                 ticket.setCusID(cusID);


                 db.collection("tickets").add(ticket).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                     @Override
                     public void onComplete(@NonNull Task<DocumentReference> task) {

                         Toast.makeText(tickets_form.this, "Ticket Add Sucessfully", Toast.LENGTH_SHORT).show();


                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {

                     }
                 });





            }
        });












    }

    private void fillOptions() {



        ArrayAdapter<String> optionadapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.options));

        optionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        option.setAdapter(optionadapter);

    }


}