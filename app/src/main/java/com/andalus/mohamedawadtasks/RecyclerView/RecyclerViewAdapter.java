package com.andalus.mohamedawadtasks.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andalus.mohamedawadtasks.R;
import com.andalus.mohamedawadtasks.database.NoteEntity;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NoteViewHolder> {

    private List<NoteEntity> noteEntities;
    private Context mContext;
    private static ClickListener clickListener;


    public RecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_recycler_view_items
                ,viewGroup,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.NoteViewHolder noteViewHolder, int i) {
        noteViewHolder.head.setTextColor(ContextCompat.getColor(mContext,checkPriority(noteEntities.get(i).getPriority())));
        noteViewHolder.subject.setText(noteEntities.get(i).getSubject());
        noteViewHolder.head.setText(noteEntities.get(i).getHead());

    }

    private int checkPriority(String priority){
        switch (priority){
            case "High":
              return R.color.High;
            case "Medium":
               return R.color.Medium;
            case "Low":
                return R.color.Less;
        }
        return 0;
    }

    public void setWord(List<NoteEntity> note){
        this.noteEntities = note;
        notifyDataSetChanged();
    }

    public NoteEntity getWordAtPosition(int position) {
        return noteEntities.get(position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerViewAdapter.clickListener = clickListener;
    }
    public interface ClickListener {
        void onItemClick(View v, int position);
    }



    @Override
    public int getItemCount() {
        if(noteEntities.size()<1){
            return 0;
        }
        return noteEntities.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView head;
        TextView subject;
        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.headTextView);
            subject = itemView.findViewById(R.id.subjectTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }

    }
    }

