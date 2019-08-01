package com.andalus.mohamedawadtasks.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andalus.mohamedawadtasks.R;
import com.andalus.mohamedawadtasks.database.UsersEntity;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MainAdapter> {


    private List<UsersEntity> entityList;

    public RecyclerAdapter(List<UsersEntity> userList) {
        this.entityList = userList;
    }


    public void setData(List<UsersEntity> list){
        this.entityList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerAdapter.MainAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MainAdapter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_items,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MainAdapter mainAdapter, int i) {
        mainAdapter.userName.setText(entityList.get(i).getUserName());
        mainAdapter.userAge.setText(entityList.get(i).getUserAge());
        mainAdapter.userGender.setText(entityList.get(i).getUserGender());
        showEyeColor(entityList.get(i).getUserEyeColor() , mainAdapter.userImage);
    }

    private void showEyeColor(String color, ImageView userImage){
        switch (color){
            case "Black":
                userImage.setImageResource(R.drawable.black);
                break;
            case "Blue":
                userImage.setImageResource(R.drawable.blue);
                break;
            case "Green":
                userImage.setImageResource(R.drawable.green);
                break;
            case "Brown":
                userImage.setImageResource(R.drawable.brown);
        }
    }


    @Override
    public int getItemCount() {
        return entityList.size();
    }


    class MainAdapter extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;
        TextView userAge;
        TextView userGender;
         MainAdapter(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
            userAge = itemView.findViewById(R.id.userAge);
            userGender = itemView.findViewById(R.id.userGender);
        }
    }
}
