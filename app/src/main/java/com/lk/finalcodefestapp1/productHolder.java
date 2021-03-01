package com.lk.finalcodefestapp1;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lk.finalcodefestapp1.Model.Product;
import com.lk.finalcodefestapp1.Model.Purchase;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class productHolder extends RecyclerView.ViewHolder {

    TextView pname,pprice;

    ImageView prdimg;
    Button purchase;

    Purchase npurchase;

    String customer;
    String productID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public productHolder(@NonNull View itemView) {
        super(itemView);

         pname= itemView.findViewById(R.id.prdname);
        pprice=  itemView.findViewById(R.id.prdprice);
         prdimg=itemView.findViewById(R.id.prdimg);

          purchase=itemView.findViewById(R.id.purchasebtn);



          purchase.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                   npurchase =new Purchase();

                   npurchase.setCustomer(customer);
                   npurchase.setPrice(pprice.getText().toString());
                   npurchase.setPrdname(pname.getText().toString());
                   npurchase.setDate(new Date());







     db.collection("Shopping").add(npurchase).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
         @Override
         public void onComplete(@NonNull Task<DocumentReference> task) {


             Toast.makeText(itemView.getContext(), " Thank u to Purchaced ", Toast.LENGTH_SHORT).show();


             updatestatus(productID);

         }
     });


              }
          });











    }

    private void updatestatus(String productID) {


        db.collection("products").document(productID).update("status","Sold").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(itemView.getContext(), "Update status", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }
}
