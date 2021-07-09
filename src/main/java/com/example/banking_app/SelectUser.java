package com.example.banking_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class SelectUser extends AppCompatActivity {

    private static final String TAG = "SelectUser";
    Database ref;
    Tansactions_Database ref2;
    private String ID,mail,toName,fromName;
    private Integer FromBalance,ToBalance,ToSend;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        Intent i=getIntent();
        ID=i.getStringExtra("from");
        ToSend=i.getIntExtra("Send",0);
        fromName=i.getStringExtra("FromName");
        FromBalance=i.getIntExtra("fromBalance",0);
        list=(ListView)findViewById(R.id.list1);
        ref = new Database(this);
        ref2=new Tansactions_Database(this);

        populateListView();
    }

    @Override
    public void onBackPressed(){
        Intent i=new Intent(SelectUser.this,MainActivity.class);
        toastMessage("Transaction Failed!!");
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
            String selId = data.getString(1).trim();
            if(!ID.equals(selId))
            {
                listData.add(selId+"\n" +data.getString(2));
            }
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        list.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mail = adapterView.getItemAtPosition(i).toString();
                mail = mail.substring(0, mail.indexOf("\n"));
                Log.d(TAG, "onItemClick: You Clicked on " + mail);

                Cursor data = ref.getName(mail); //get the id associated with that name
                while(data.moveToNext()){
                    toName = data.getString(0);
                }

                data = ref.getBalance(mail); //get the id associated with that name
                ToBalance = -1;
                while(data.moveToNext()){
                    ToBalance = data.getInt(0);
                }
                if(ToBalance > -1){
                    AlertDialog.Builder builder=new AlertDialog.Builder(SelectUser.this);
                    builder.setMessage("Confirm Transfer to "+mail+" ?")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd     HH:mm:ss");
                                    LocalDateTime now = LocalDateTime.now();
                                    String time_now=dtf.format(now).toString();
                                    ref2.addData(toName,fromName,ToSend,time_now);
                                    ref.updateBalance(ToBalance+ToSend,mail,ToBalance);
                                    ref.updateBalance(FromBalance-ToSend,ID,FromBalance);
                                    toastMessage("Transaction Successful!!");
                                    Intent i1=new Intent(SelectUser.this,MainActivity.class);
                                    startActivity(i1);
                                    finish();
                                }
                            })
                            .setNegativeButton("Cancel",null);
                    AlertDialog alert=builder.create();
                    alert.show();

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