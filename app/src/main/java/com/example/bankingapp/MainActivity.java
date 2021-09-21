package com.example.bankingapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bankingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=activityMainBinding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        SQLiteDatabase userDatabase=this.openOrCreateDatabase("User",MODE_PRIVATE,null);
        userDatabase.execSQL("CREATE TABLE IF NOT EXISTS users(user_id VARCHAR(3),name VARCHAR(20),email VARCHAR(20),current_balance FLOAT)");
        SQLiteDatabase transactionDatabase=this.openOrCreateDatabase("Transaction",MODE_PRIVATE,null);
        transactionDatabase.execSQL("CREATE TABLE IF NOT EXISTS transactions(sender VARCHAR(20),reciever VARCHAR(20),amount FLOAT(5),isSuccessful INT(1))");
        Cursor c=userDatabase.rawQuery("Select * from Users",null);
        if(c.getCount()==0){
            userDatabase.execSQL("INSERT INTO users values('45','Rohit','rohit45@gmail.com',80000)");
            userDatabase.execSQL("INSERT INTO users values('1','Rahul','rahul01@gmail.com',45000)");
            userDatabase.execSQL("INSERT INTO users values('18','Virat','virat18@gmail.com',99000)");
            userDatabase.execSQL("INSERT INTO users values('63','Surya','surya63@gmail.com',30000)");
            userDatabase.execSQL("INSERT INTO users values('77','Rishabh','rishabh77@gmail.com',35000)");
            userDatabase.execSQL("INSERT INTO users values('33','Hardik','hardik33@gmail.com',37000)");
            userDatabase.execSQL("INSERT INTO users values('8','Jadeja','jadoo8@gmail.com',40000)");
            userDatabase.execSQL("INSERT INTO users values('99','Ashwin','ash99@gmail.com',40000)");
            userDatabase.execSQL("INSERT INTO users values('93','Jasprit','boom93@gmail.com',75000)");
            userDatabase.execSQL("INSERT INTO users values('15','Kumar','bhuvi15@gmail.com',45000)");
        }

        activityMainBinding.viewCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UserList.class);
                startActivity(intent);
            }
        });
        activityMainBinding.viewTransactionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),TransactionActivity.class);
                startActivity(intent);
            }
        });
    }
}