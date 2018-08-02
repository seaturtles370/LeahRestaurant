package com.example.lana.leahrestaurant;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.net.URI;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String COORDINATES = "38.4088, 121.3716";

    @OnClick(R.id.location)
    public void clickLocation(View view){
        Uri uri = Uri.parse("geo: " + COORDINATES);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
