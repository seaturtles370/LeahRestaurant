package com.example.lana.leahrestaurant;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class Order extends AppCompatActivity {


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
    int maxDays = 1;
    boolean delete = false;

    String CURRENT_FILE_NAME = "order.txt";
    File current = new File("/data/user/0/com.example.lana.leahrestaurant/files/" + CURRENT_FILE_NAME);
    FileOutputStream fos = null;


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

        submitButton.setVisibility(View.INVISIBLE);
        quantityTextView.setText("Quantity: " + quantityNumber);


        //Bdd Items Button Listener
        addToOrder.setOnClickListener(view -> {
            quantityNumber = 1;
            quantityTextView.setText("Quantity: " + quantityNumber);
            nameBlank.setVisibility(View.INVISIBLE);
            nameQuestion.setVisibility(View.INVISIBLE);
            submitButton.setVisibility(View.VISIBLE);
            addItem(wholeOrder);
            nameBlank.getText().clear();
            itemBlank.getText().clear();
        });

        submitButton.setOnClickListener(view -> {
            Date currentTime = Calendar.getInstance().getTime();
            wholeOrder.add(0,String.valueOf(currentTime));
            if(itemBlank != null){
                addItem(wholeOrder);
            }
            quantityNumber = 1;
            quantityTextView.setText("Quantity: " + quantityNumber);
            nameBlank.setVisibility(View.VISIBLE);
            nameQuestion.setVisibility(View.VISIBLE);
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
        if(order != null){
            ordered.add(String.valueOf(quantityNumber) +" "+ order);
        }else{
            ordered.add("");
        }
        return ordered;
    }

    private String puttingDataInAString(ArrayList<String> listOfItemsOrdered){
        String totalOrder = "";
        listOfItemsOrdered.remove(listOfItemsOrdered.get(listOfItemsOrdered.size()-1));
        for(String item: listOfItemsOrdered){
            totalOrder = "\n" + totalOrder+ "\n" + item;
        }
        return totalOrder;
    }

    private void saveToLocalStorage(String order) {

        //Dealing with time.
        long lastModified = current.lastModified();
        Date dt = new Date(lastModified);
        long difference = System.currentTimeMillis() - dt.getTime();
        long time = maxDays * 24 * 60 * 60 * 1000;
        String text = order;


        if (current.exists()) {
            if(difference > time){
                if(current.delete()){
                    writeToSpecificFile(makeNewTextFile(), text);
                    CURRENT_FILE_NAME = makeNewTextFile();
                }else{
                    Toast.makeText(this, "WTF", Toast.LENGTH_SHORT).show();
                }
            }else{
                writeToSpecificFile(CURRENT_FILE_NAME, text);
            }
        }else{
            writeToSpecificFile(makeNewTextFile(),text);
            //Meaning the specified time didn't pass yet.
        }

    }

    private String makeNewTextFile(){
        String NEW_FILE_NAME = "order.txt";
        return NEW_FILE_NAME;
    }

        public void writeToSpecificFile(String fileName, String textOrder){
            try {
                fos = openFileOutput(fileName,MODE_APPEND);
                fos.write(textOrder.getBytes());
                Toast.makeText(this, "Saved to " + getFilesDir().getAbsolutePath() + "/" + fileName,Toast.LENGTH_LONG).show();
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

