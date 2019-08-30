package com.andalus.mohamedawadtasks;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andalus.mohamedawadtasks.mediaPlayer.Mp3File;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Mp3File> mp3FileArrayList = null;
    private Context mContext;
    private static ClickListener clickListener;

    RecyclerViewAdapter(Context context, ArrayList<Mp3File> mp3Files) {
        mp3FileArrayList = mp3Files;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_items, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.songTitle.setText(mp3FileArrayList.get(i).getTitle());
        viewHolder.artist.setText(mp3FileArrayList.get(i).getArtist());
        getAlbumArt(viewHolder.imageView, i);

    }

    @Override
    public int getItemCount() {
        if(mp3FileArrayList != null) {
            return mp3FileArrayList.size();
        }else{

            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView songTitle;
        TextView artist;
        ImageView imageView;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.songName);
            artist = itemView.findViewById(R.id.artist);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    void setOnItemClickListener(ClickListener clickListener) {
        RecyclerViewAdapter.clickListener = clickListener;
    }

    private void getAlbumArt(ImageView imageView, int pos) {
        try {
            final Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");
            Uri uri = ContentUris.withAppendedId(sArtworkUri, mp3FileArrayList.get(pos).getAlbum());
            ContentResolver res = mContext.getContentResolver();
            InputStream in = res.openInputStream(uri);
            imageView.setImageBitmap(BitmapFactory.decodeStream(in));
        } catch (FileNotFoundException e) {
            imageView.setImageResource(R.drawable.ic_musical_note);
        }
    }

    public interface ClickListener {
        void onItemClick(int pos, View v);
    }
}

