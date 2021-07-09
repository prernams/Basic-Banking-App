package com.example.banking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Transfer extends AppCompatActivity {

    private TextView Balance,Name;
    private String ID,fromName;
    private Integer balance,toSend;
    private EditText Amount;
    private Button transfer;
    Database ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        Intent i=getIntent();
        ID=i.getStringExtra("id");
        balance=i.getIntExtra("balance",0);
        fromName=i.getStringExtra("FromName");
        Balance=(TextView)findViewById(R.id.bal);
        Amount=(EditText)findViewById(R.id.Amount);
        transfer=(Button)findViewById(R.id.transfer);
        Name=(TextView)findViewById(R.id.Name);
        Name.setText("Hello "+fromName);
        Balance.setText("Rs. "+balance.toString()+"/-");

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Amount.getText()))
                {
                    Amount.setError("Please Enter the Amount to Transfer");
                }
                else
                {
                    toSend=Integer.parseInt(Amount.getText().toString());
                    if(toSend>balance)
                    {
                        toastMessage("Insufficient Balance");
                    }
                    else
                    {
                        Intent i1=new Intent(Transfer.this,SelectUser.class);
                        i1.putExtra("Send",toSend);
                        i1.putExtra("FromName",fromName);
                        i1.putExtra("from",ID);
                        i1.putExtra("fromBalance",balance);
                        startActivity(i1);
                        finish();
                    }
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent i=new Intent(Transfer.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}