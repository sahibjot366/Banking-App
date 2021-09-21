package com.example.bankingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bankingapp.databinding.ActivityUserListBinding;

import java.util.ArrayList;

public class UserList extends AppCompatActivity implements UserlistAdapter.OnUserListener {
    private ActivityUserListBinding activityUserListBinding;
     static ArrayList<User> userList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserListBinding=ActivityUserListBinding.inflate(getLayoutInflater());
        View view=activityUserListBinding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        SQLiteDatabase userDatabase=this.openOrCreateDatabase("User",MODE_PRIVATE,null);
        Cursor c=userDatabase.rawQuery("Select * from Users",null);
        int nameIndex=c.getColumnIndex("name");
        int currentBalanceIndex=c.getColumnIndex("current_balance");
        int userIdIndex=c.getColumnIndex("user_id");
        int emailIndex=c.getColumnIndex("email");
        if(userList.size()>0)
            userList.clear();
        c.moveToFirst();
        while(!c.isAfterLast()) {
            userList.add(new User(c.getString(userIdIndex),c.getString(nameIndex),c.getString(emailIndex),c.getFloat(currentBalanceIndex)));
            c.moveToNext();
        }
        UserlistAdapter userlistAdapter=new UserlistAdapter(userList,this);
        userlistAdapter.notifyDataSetChanged();
        activityUserListBinding.userListRecycleView.setAdapter(userlistAdapter);
        activityUserListBinding.userListRecycleView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void OnUserClick(int position) {
        Intent intent=new Intent(getApplicationContext(),SelectedUser.class);
        intent.putExtra("uid",userList.get(position).getUser_id());
        intent.putExtra("name",userList.get(position).getName());
        intent.putExtra("email",userList.get(position).getEmail());
        intent.putExtra("balance",userList.get(position).getCurrent_balance());
        intent.putExtra("position",position);
        finish();
        startActivity(intent);
    }
}