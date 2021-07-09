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

import java.util.ArrayList;

public class Transactions extends AppCompatActivity {

    private static final String TAG = "Transactoins";
    Tansactions_Database ref;
    private ProgressDialog pd;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        pd=new ProgressDialog(Transactions.this);
        pd.setTitle("Loading Data...");
        pd.show();
        list=(ListView)findViewById(R.id.list2);
        ref = new Tansactions_Database(this);
        populateListView();
    }

    @Override
    public void onBackPressed(){
        Intent i=new Intent(Transactions.this,Welcome_Page.class);
        startActivity(i);
        finish();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = ref.getData();
        int i=0;
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext())
        {
            listData.add(data.getString(2)+" -----> " +data.getString(1)+"\nRs. "+data.getString(3)+"\n"+data.getString(4)+"\n");
            i++;
        }
        ArrayList<String> listDataReverse = new ArrayList<>();
        while (i>0)
        {
            listDataReverse.add(listData.get(--i));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listDataReverse);
        pd.dismiss();
        list.setAdapter(adapter);

    }

}