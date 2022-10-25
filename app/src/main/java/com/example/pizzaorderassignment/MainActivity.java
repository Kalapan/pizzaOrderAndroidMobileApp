package com.example.pizzaorderassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText specialInstructions, name, address, phone, email;
    Spinner toppings;
    TextView livePrice;
    DecimalFormat formatVar =  new DecimalFormat("0.00");
    double totalAmount = 0;
    double pizzaAmount = 0;
    double extraCheese = 0;
    double toppingPrice = 0;
    double includeDelivery = 0;
    String chosenTopping = "";
    StringBuilder userOrder = new StringBuilder();
    StringBuilder userInfo = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign all the objects from user interface to variables by getting their ids
        toppings = findViewById(R.id.spinnerTopping);
        specialInstructions =  findViewById(R.id.specialInstruction);
        name =  findViewById(R.id.name);
        address =  findViewById(R.id.address);
        phone =  findViewById(R.id.phone);
        email =  findViewById(R.id.email);
        livePrice =  findViewById(R.id.livePrice);


        //create adapter to get array list from resources file
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.toppings));
        //make it a dropdown
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //show the data in the spinner
        toppings.setAdapter(myAdapter);

        //make it so when any option is clicked on the dropdown menu it can update values
        toppings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //set the chosen topping to a string
                chosenTopping = toppings.getSelectedItem().toString();
                //using the index to find what is selected, assign a price
                if (i == 1)
                    toppingPrice = 5;
                else if (i == 2)
                    toppingPrice = 5;
                else if (i == 3)
                    toppingPrice = 7;
                else if (i == 4)
                    toppingPrice = 8;
                else if (i == 5)
                    toppingPrice = 10;
                else if (i == 6)
                    toppingPrice = 5;
                else if (i == 7)
                    toppingPrice = 9;
                else if (i == 8)
                    toppingPrice = 5;
                else if (i == 9)
                    toppingPrice = 5;
                else if (i == 10)
                    toppingPrice = 8;

                //update the live price everytime a new object is selected in the dropdown menu
                livePrice.setText("Total Price is: " + calculate_total());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //when the submit button is pressed
    public void submitPressed(View v) {

        //based on what pizza amount is selected, append what size pizza is selected to userOrder which is passed to the other activity
        if (pizzaAmount == 5.50) {
            userOrder.append("\nYou have ordered a round pizza that has 6 slices and serves 3 people.");
        }
        if (pizzaAmount == 7.99) {
            userOrder.append("\nYou have ordered a round pizza that has 8 slices and serves 4 people.");
        }
        if (pizzaAmount == 9.50) {
            userOrder.append("\nYou have ordered a round pizza that has 10 slices and serves 5 people.");
        }
        if (pizzaAmount == 11.38) {
            userOrder.append("\nYou have ordered a round pizza that has 12 slices and serves 6 people.");
        }

        //append the chosen topping to userOrder to get it to the other activity
        userOrder.append("\nThe topping you have chosen is: " + chosenTopping);

        //if extra cheese or include delivery is selected append corresponding message
        if (extraCheese == 5){
            userOrder.append("\nYou have requested Extra Cheese.");
        }
        if (includeDelivery == 5){
            userOrder.append("\nYou have requested Delivery.");
        }

        //append users contact details to userinfo to send to other activity
        userInfo.append("Name: " + name.getText().toString() + "\nAddress: " + address.getText().toString() + "\nPhone: " + phone.getText().toString() + "\nEmail: " + email.getText().toString());
        //append users special instructions to userOrder
        userOrder.append("\n\nSpecial Instructions: " + specialInstructions.getText().toString());

        //intent is used to send information from one activity to another
        Intent sender = new Intent(this, orderSummary.class);
        //putExtra used along with keys which will be used to obtain each variable on the other activity
        sender.putExtra("userOrder_key", (CharSequence) userOrder);
        sender.putExtra("userInfo_key", (CharSequence) userInfo);
        sender.putExtra("cost_key", calculate_total());
        //start the other activity when the button is clicked
        startActivity(sender);
    }

    public void onRadioButtonClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radioButton was clicked
        //based on what button is clicked assign the corresponding pizza cost to pizzaAmount
        switch(view.getId()) {
            case R.id.sixSlice:
                if (checked) {
                    pizzaAmount = 5.50;
                }
                break;
            case R.id.eightSlice:
                if (checked) {
                    pizzaAmount = 7.99;
                }
                break;
            case R.id.tenSlice:
                if (checked) {
                    pizzaAmount = 9.50;
                }
                break;
            case R.id.twelveSlice:
                if (checked) {
                    pizzaAmount = 11.38;
                }
                break;
        }
        //update the price live as user clicks through all radio buttons
        livePrice.setText("Total Price is: $" + calculate_total());
    }

    public void onExtraCheeseChecked(View view) {
        //check is checkbox for extra cheese is selected, and if so set the price
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            extraCheese = 5;
        }
        //if it isnt selected set the price to 0
        else {
            extraCheese = 0;
        }
        //update the price live
        livePrice.setText("Total Price is: $" + calculate_total());
    }

    public void onIncludeDeliveryChecked(View view) {
        //check if include deliver checkbox is selected
        boolean checked = ((CheckBox) view).isChecked();
        //if selected set the price to $5
        if (checked) {
            includeDelivery = 5;
        }
        //if not set the price to 0
        else {
            includeDelivery = 0;
        }
        //update price live everytime checkbox is pressed
        livePrice.setText("Total Price is: $" + calculate_total());
    }

    private String calculate_total() {
        //calculate the total price by adding all the various amounts
        totalAmount = pizzaAmount + includeDelivery + extraCheese + toppingPrice;
        return formatVar.format(totalAmount);
    }
}