package com.example.bankingapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserlistAdapter extends
        RecyclerView.Adapter<UserlistAdapter.ViewHolder> {
    private ArrayList<User> userArrayList;
    private OnUserListener monUserListener;
    public UserlistAdapter(ArrayList<User> userList,OnUserListener onUserListener) {

        userArrayList = userList;
        this.monUserListener=onUserListener;
    }

    @Override
    public UserlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.userlist_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView,monUserListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserlistAdapter.ViewHolder holder, int position) {
        User user = userArrayList.get(position);
        TextView uidText = holder.uid;
        uidText.setText(user.getUser_id());
        TextView nameText=holder.name;
        nameText.setText(user.getName());
        TextView currBalText=holder.curr_balance;
        currBalText.setText("₹"+String.valueOf(user.getCurrent_balance()));
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView uid,name,curr_balance;
        OnUserListener onUserListener;
        public ViewHolder(View itemView,OnUserListener onUserListener) {
            super(itemView);
            uid=(TextView)itemView.findViewById(R.id.uidTextView);
            name=(TextView)itemView.findViewById(R.id.nameTextView);
            curr_balance=(TextView)itemView.findViewById(R.id.balanceTextView);
            this.onUserListener=onUserListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onUserListener.OnUserClick(getAdapterPosition());
        }
    }
    public interface OnUserListener{
        void OnUserClick(int position);
    }
}
