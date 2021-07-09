package com.example.banking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private com.google.android.material.floatingactionbutton.FloatingActionButton add;
    private String name;
    private ProgressDialog pd;
    Database ref;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pd=new ProgressDialog(MainActivity.this);
        pd.setTitle("Loading Data...");
        pd.show();
        list=(ListView)findViewById(R.id.list);
        add=(com.google.android.material.floatingactionbutton.FloatingActionButton)findViewById(R.id.b1);
        ref = new Database(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(MainActivity.this,New_Customer.class);
                startActivity(i2);
            }
        });

        populateListView();
    }
    @Override
    public void onBackPressed(){
        Intent i=new Intent(MainActivity.this,Welcome_Page.class);
        startActivity(i);
        finish();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = ref.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext())
        {
            listData.add(data.getString(1)+"\n" +data.getString(2)+"\nCurrent Balance: Rs. "+data.getString(3)+"\n");
            //listData.add(data.getString(2));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        pd.dismiss();
        list.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mail = adapterView.getItemAtPosition(i).toString();
                mail = mail.substring(0, mail.indexOf("\n"));
                Log.d(TAG, "onItemClick: You Clicked on " + mail);

                Cursor data = ref.getBalance(mail); //get the id associated with that name
                int balance = -1;
                while(data.moveToNext()){
                    balance = data.getInt(0);
                }
                data = ref.getName(mail);
                while(data.moveToNext()){
                    name = data.getString(0);
                }

                if(balance > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + balance);
                    Intent i1 = new Intent(MainActivity.this, Transfer.class);
                    i1.putExtra("id",mail);
                    i1.putExtra("FromName",name);
                    i1.putExtra("balance",balance);
                    startActivity(i1);
                    finish();
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}