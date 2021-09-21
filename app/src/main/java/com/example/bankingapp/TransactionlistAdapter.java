package com.example.bankingapp;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionlistAdapter extends
        RecyclerView.Adapter<TransactionlistAdapter.ViewHolder> {
    private ArrayList<Transaction> transactionArrayList;

    public TransactionlistAdapter(ArrayList<Transaction> transactionList) {

        transactionArrayList = transactionList;
    }

    @Override
    public TransactionlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View transactionView = inflater.inflate(R.layout.transaction_layout, parent, false);
        ViewHolder viewHolder=new ViewHolder(transactionView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TransactionlistAdapter.ViewHolder holder, int position) {
        Transaction transaction=transactionArrayList.get(position);
        TextView senderText = holder.sender;
        senderText.setText("From: "+transaction.getSender());
        TextView recieverText = holder.reciever;
        recieverText.setText("To: "+transaction.getReciever());
        TextView amountText=holder.amount;
        if(transaction.getIsSuccessful()==1){
            amountText.setText("Amount: ₹"+transaction.getAmount().toString()+"\nSuccessful Transacion");
            amountText.setTextColor(Color.parseColor("#00FF00"));
        }
        else{
            amountText.setText("Amount: ₹"+transaction.getAmount().toString()+"\nCancelled Transaction");
            amountText.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView sender,reciever,amount;
        public ViewHolder(View itemView) {
            super(itemView);
            sender=(TextView)itemView.findViewById(R.id.senderTextView);
            reciever=(TextView)itemView.findViewById(R.id.recieverTextView);
            amount=(TextView)itemView.findViewById(R.id.amountTextView);
        }


}


}
