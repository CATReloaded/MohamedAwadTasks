package com.andalus.mohamedawadtasks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MainAdapter>  {

    private AppData[] app;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener mListener){
        listener = mListener;
    }

    public RecyclerViewAdapter(AppData[] app){
        this.app = app; }

    @NonNull
    @Override
    public MainAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.app_items
                , viewGroup,false);
        return new MainAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter mainAdapter, int i) {
        mainAdapter.appImage.setImageResource(app[i].getAppImage());
        mainAdapter.appName.setText(app[i].getAppName());
        mainAdapter.appSize.setText(String.valueOf(app[i].getAppSize()) + " Mb");
    }

    @Override
    public int getItemCount() {
        return this.app.length;
    }


    class MainAdapter extends RecyclerView.ViewHolder {

        ImageView appImage;
        TextView appName;
        TextView appSize;
        public MainAdapter(@NonNull View itemView) {
            super(itemView);
             appImage = itemView.findViewById(R.id.imageView);
             appName = itemView.findViewById(R.id.appName);
             appSize = itemView.findViewById(R.id.appSize);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(listener != null){
                         int pos = getAdapterPosition();
                         if(pos != RecyclerView.NO_POSITION){
                             listener.onItemClick(pos);
                         }
                     }
                 }
             });

        }

    }



}
