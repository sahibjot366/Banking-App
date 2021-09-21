package com.example.bankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {
    ArrayList<Transaction> transactionArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        getSupportActionBar().hide();
        recyclerView=(RecyclerView) findViewById(R.id.transactionListRecyclerView);
        SQLiteDatabase transactionDatabase=this.openOrCreateDatabase("Transaction",MODE_PRIVATE,null);
        Cursor c=transactionDatabase.rawQuery("SELECT * FROM transactions",null);
        if(c.getCount()==0){
            Toast.makeText(this, "No transactions yet!!", Toast.LENGTH_LONG).show();
        }
        int senderIndex=c.getColumnIndex("sender");
        int recieverIndex=c.getColumnIndex("reciever");
        int amountIndex=c.getColumnIndex("amount");
        int isSuccessfulIndex=c.getColumnIndex("isSuccessful");
        c.moveToFirst();
        while(!c.isAfterLast()){
            transactionArrayList.add(new Transaction(c.getString(senderIndex),c.getString(recieverIndex),c.getFloat(amountIndex),c.getInt(isSuccessfulIndex)));
            c.moveToNext();
        }
        TransactionlistAdapter transactionlistAdapter=new TransactionlistAdapter(transactionArrayList);
        transactionlistAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(transactionlistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
