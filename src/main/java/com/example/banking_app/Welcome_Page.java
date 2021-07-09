package com.example.banking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome_Page extends AppCompatActivity {

    private Button Transactions,ViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome__page);

        Transactions=(Button)findViewById(R.id.transaction);
        ViewList=(Button)findViewById(R.id.List);

        Transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(Welcome_Page.this, Transactions.class);
                startActivity(i1);
                finish();
            }
        });

        ViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(Welcome_Page.this, MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
    }
}