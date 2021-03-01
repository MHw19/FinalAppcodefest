package com.lk.finalcodefestapp1;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsHolder  extends RecyclerView.ViewHolder {

    TextView etitle,edes,eloc;
     Button direct;

     String newsID;

    public NewsHolder(@NonNull View itemView) {
        super(itemView);

        etitle=itemView.findViewById(R.id.evetiltle);
        edes= itemView.findViewById(R.id.evedess);
        eloc=itemView.findViewById(R.id.evelocation);


        direct = itemView.findViewById(R.id.directbtn);





        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(itemView.getContext(),Directionpage.class);

                intent.putExtra("NewsID",newsID);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                itemView.getContext().startActivity(intent);


            }
        });







    }
}
