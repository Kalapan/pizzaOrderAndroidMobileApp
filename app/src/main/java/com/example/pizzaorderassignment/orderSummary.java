package com.example.pizzaorderassignment;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class orderSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        //assign all objects using their ids
        TextView userOrder =  findViewById(R.id.userOrderInfoDisplay);
        TextView userInfo =  findViewById(R.id.userInfoDisplay);
        TextView cost =  findViewById(R.id.costDisplay);

        //get all the data passed from other activity and assign them to variables
        Intent receiverIntent = getIntent();
        String order = receiverIntent.getStringExtra("userOrder_key");
        String info = receiverIntent.getStringExtra("userInfo_key");
        String totalPrice = receiverIntent.getStringExtra("cost_key");

        //set the variables to text views to display
        userOrder.setText(order);
        userInfo.setText(info);

        //use stringBuilder to append a money symbol
        StringBuilder costWithPriceSymbol = new StringBuilder();
        costWithPriceSymbol.append("$" + totalPrice);

        //display cost with symbol
        cost.setText(costWithPriceSymbol);

        //when orderEntry button is pressed go back to previous activity
        Button orderEntry = findViewById(R.id.orderEntry);
        orderEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(orderSummary.this, MainActivity.class));
            }
        });
    }
}