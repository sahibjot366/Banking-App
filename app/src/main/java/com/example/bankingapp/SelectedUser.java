package com.example.bankingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bankingapp.databinding.ActivitySelectedUserBinding;
import java.text.MessageFormat;
import java.util.ArrayList;

public class SelectedUser extends AppCompatActivity {
    private ActivitySelectedUserBinding activitySelectedUserBinding;
    ArrayList<User> sendList=new ArrayList<>();
    static int selected_option=-1;
    Intent intent;
    SQLiteDatabase transactionDatabase;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),UserList.class);
        startActivity(intent);
    }
    public static boolean onlyDigits(String str, int n)
    {
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) >= '0'
                    && str.charAt(i) <= '9') { }
            else {
                return false;
            }
        }
        return true;
    }
    public void saveTransactionSummary(int isSuccesfull,int selected_option,EditText editText){
        String sender=" "+UserList.userList.get(intent.getIntExtra("position",0)).getName()+" ";
        String reciever=" "+sendList.get(selected_option).getName()+" ";
        Float amount=Float.parseFloat(editText.getText().toString());
        int isSuccessful = isSuccesfull;
        String transactionCommand = "INSERT INTO transactions(amount,isSuccessful,sender,reciever) values( "+ amount + "," + isSuccessful + ",\""+sender+ "\",\""+reciever+"\");";
        transactionDatabase.execSQL(transactionCommand);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySelectedUserBinding=ActivitySelectedUserBinding.inflate(getLayoutInflater());
        View view=activitySelectedUserBinding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        SQLiteDatabase userDatabase=this.openOrCreateDatabase("User",MODE_PRIVATE,null);
        transactionDatabase=this.openOrCreateDatabase("Transaction",MODE_PRIVATE,null);
        sendList.addAll(UserList.userList);
        intent=getIntent();
        sendList.remove(intent.getIntExtra("position",0));
        String[] names=new String[sendList.size()];
        for(int i=0;i<sendList.size();i++){
            names[i]=sendList.get(i).getName();
        }
        activitySelectedUserBinding.uidselectedUser.setText("Uid : "+intent.getStringExtra("uid"));
        activitySelectedUserBinding.nameselectedUser.setText("Name : "+intent.getStringExtra("name"));
        activitySelectedUserBinding.emailselectedUser.setText("Email : "+intent.getStringExtra("email"));
        activitySelectedUserBinding.balanceselectedUser.setText("Current Balance\n₹"+intent.getFloatExtra("balance",0));
        activitySelectedUserBinding.monetTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(SelectedUser.this);
                builder.setTitle("Whom to transfer money?");
                builder.setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected_option=which;
                        EditText editText=new EditText(SelectedUser.this);
                        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        AlertDialog alertDialog=new AlertDialog.Builder(SelectedUser.this)
                                .setView(editText)
                                .setMessage("Enter amount to transfer : ")
                                .setPositiveButton("Transfer",null)
                                .setNegativeButton("Cancel", null)
                                .show();
                        Button positivebutton=alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positivebutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(editText.getText().toString().isEmpty()){
                                    editText.setError("Amount can't be empty");
                                }
                                else if(!onlyDigits(editText.getText().toString(),editText.getText().toString().length())){
                                    editText.setText("");
                                    editText.setError("Only digits are allowed");
                                }
                                else if(Float.parseFloat(editText.getText().toString())>UserList.userList.get(intent.getIntExtra("position",0)).getCurrent_balance()){
                                    editText.setText("");
                                    editText.setError("You don't have sufficient balance");
                                }
                                else{
                                    AlertDialog confirmationAlertDialog=new AlertDialog.Builder(SelectedUser.this)
                                            .setTitle("Proceed or not?")
                                            .setMessage(Float.parseFloat(editText.getText().toString())+" Rs will be transfered")
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    saveTransactionSummary(0,selected_option,editText);
                                                }
                                            })
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    alertDialog.dismiss();
                                                    ProgressDialog progressDialog=new ProgressDialog(SelectedUser.this);
                                                    progressDialog.setMessage("Transaction is being Done");
                                                    progressDialog.show();
                                                    String decamt="UPDATE users SET current_balance = current_balance - "+editText.getText()+" WHERE user_id = "+UserList.userList.get(intent.getIntExtra("position",0)).getUser_id();
                                                    String incamt="UPDATE users SET current_balance = current_balance + "+editText.getText()+" WHERE user_id = "+sendList.get(selected_option).getUser_id();
                                                    try {
                                                        userDatabase.execSQL("BEGIN TRANSACTION");
                                                        userDatabase.execSQL(decamt);
                                                        userDatabase.execSQL(incamt);
                                                        userDatabase.execSQL("COMMIT");
                                                        try {
                                                            saveTransactionSummary(1,selected_option,editText);
                                                        }
                                                        catch (Exception e){
                                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                        Toast.makeText(getApplicationContext(), "Transaction successfull :)", Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent(getApplicationContext(), UserList.class);
                                                        finish();
                                                        startActivity(intent);
                                                    }
                                                    catch (Exception e){
                                                        Toast.makeText(SelectedUser.this, "Transaction failed :(", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }).show();
                                }
                            }
                        });

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
    }
}