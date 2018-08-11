package com.example.lana.leahrestaurant;

import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Order extends AppCompatActivity {
    private static final String FILE_NAME = "orders.txt";

    private TextView quantityTextView;
    private Button addToQuantityButton;
    private Button minusQuantityButton;
    private EditText nameBlank;
    private TextView nameQuestion;
    private Button addToOrder;
    private EditText itemBlank;
    private TextView orderLabel;
    private Button submitButton;
    ArrayList<String> wholeOrder = new ArrayList<String>();
    int quantityNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //View Objects Instantiation
        quantityTextView = findViewById(R.id.quantity);
        addToQuantityButton = findViewById(R.id.addQuantityButt);
        minusQuantityButton = findViewById(R.id.minusQuantityButt);
        nameBlank = findViewById(R.id.nameBox);
        nameQuestion = findViewById(R.id.nameLabel);
        addToOrder = findViewById(R.id.addItemButton);
        itemBlank = findViewById(R.id.itemBox);
        orderLabel = findViewById(R.id.orderLabel);
        submitButton = findViewById(R.id.submitButton);

        //Bdd Items Button Listener
        addToOrder.setOnClickListener(view -> {
            nameBlank.setVisibility(View.INVISIBLE);
            nameQuestion.setVisibility(View.INVISIBLE);
            addItem(wholeOrder);
        });

        submitButton.setOnClickListener(view -> {
            saveToLocalStorage(puttingDataInAString(wholeOrder));
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void quantity(View view){
        boolean addButtonIsPressed = addToQuantityButton.isPressed();
        boolean subtractButtonIsPressed = minusQuantityButton.isPressed();
        if(addButtonIsPressed){
            quantityNumber = quantityNumber + 1;
        }else if(subtractButtonIsPressed){
            quantityNumber = quantityNumber - 1;
            if(quantityNumber < 1){
                quantityNumber = 1;
            }
        }
        quantityTextView.setText("Quantity: " + quantityNumber);
    }

    public ArrayList<String> addItem(ArrayList<String> ordered){
        String personName = nameBlank.getText().toString();
        String order = itemBlank.getText().toString();
        ordered.add(personName);
        ordered.add(quantityNumber +" "+ order);
        nameBlank.getText().clear();
        itemBlank.getText().clear();
        quantityNumber = 0;
        return ordered;
    }

    private String puttingDataInAString(ArrayList<String> listOfItemsOrdered){
        String totalOrder = "";
        for(String item: listOfItemsOrdered){
            totalOrder = totalOrder+ "\n" + item;
        }
        return totalOrder;
    }

    private void saveToLocalStorage(String order){
        String text = order;
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
            fos.write(text.getBytes());
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
