package com.example.banking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class New_Customer extends AppCompatActivity {

    private EditText name,mail;
    private String Name,Mail;
    private Button submit;
    Database ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__customer);

        name=(EditText)findViewById(R.id.name);
        mail=(EditText)findViewById(R.id.mail);
        submit=(Button)findViewById(R.id.submit);
        ref = new Database(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name=name.getText().toString();
                Mail=mail.getText().toString();
                ref.addData(Name,Mail,10000);

                Intent i=new Intent(New_Customer.this,MainActivity.class);
                toastMessage("Customer Added!");
                startActivity(i);
                finish();

            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}