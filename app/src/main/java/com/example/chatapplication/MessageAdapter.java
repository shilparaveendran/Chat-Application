package com.example.chatapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    Context context;
    List<MessageModel>messageModelList;

    public MessageAdapter(Context context) {
        this.context = context;
        messageModelList=new ArrayList<>();
    }
    public  void add(MessageModel messageModel){
        messageModelList.add(messageModel);
        notifyDataSetChanged();
    }
    public  void clear(){
        messageModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
    MessageModel messageModel  = messageModelList.get(position);
    holder.msg.setText(messageModel.getMessage());
    if(messageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid())){
        holder.main.setBackgroundColor(context.getResources().getColor(R.color.teal_200));
        holder.msg.setBackgroundColor(context.getResources().getColor(R.color.white));

    }else{
        holder.main.setBackgroundColor(context.getResources().getColor(R.color.black));
        holder.msg.setBackgroundColor(context.getResources().getColor(R.color.white));
    }

    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView msg;
        private LinearLayout main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            msg = itemView.findViewById(R.id.message);
            main = itemView.findViewById(R.id.mainMessageLayout);
        }
    }
}
