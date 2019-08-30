package com.andalus.mohamedawadtasks.mediaPlayer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.andalus.mohamedawadtasks.MusicNotification;
import com.andalus.mohamedawadtasks.services.MediaPlayerService;

import java.io.IOException;
import java.util.ArrayList;

public class ListOfMp3FromStorage {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    public static ArrayList<Mp3File> mp3Files;
    private static MediaPlayer player = new MediaPlayer();


    public static void requestRead(Context context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            scanDeviceForMp3Files(context);
        }
    }


    private static void scanDeviceForMp3Files(Context context) {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID
        };
        final String sortOrder = MediaStore.Audio.AudioColumns.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        mp3Files = new ArrayList<>();

        Cursor cursor = null;
        try {
            Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            cursor = context.getContentResolver().query(uri, projection, selection, null, sortOrder);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String title = cursor.getString(0);
                    String artist = cursor.getString(1);
                    String path = cursor.getString(2);
                    String displayName = cursor.getString(3);
                    String songDuration = cursor.getString(4);
                    Long album = cursor.getLong(5);
                    cursor.moveToNext();
                    if (path != null && path.endsWith(".mp3")) {
                        mp3Files.add(new Mp3File(title, artist, path, displayName, songDuration, album));
                    }
                }
            }
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static void runMediaPlayer(final Context context, final int pos) {
        try {
            closeMediaPlayer();
            player.setDataSource(mp3Files.get(pos).getPath());
            playOrPauseMediaPlayer(context);
            MusicNotification.changeImageToPause();
        } catch (IOException e) {
            Log.v("TAG", e.toString());
        }
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                forwardMediaPlayer(context, pos);
            }
        });
    }

    public static void playOrPauseMediaPlayer(Context context) {
        int length = player.getCurrentPosition();
        if (player.isPlaying()) {
            player.pause();
            MusicNotification.changeImageToPlay();
        } else if (length > 0) {
            length = player.getCurrentPosition();
            player.seekTo(length);
            player.start();
            MusicNotification.changeImageToPause();
        } else {
            try {
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void forwardMediaPlayer(Context context, int pos) {
        try {
            closeMediaPlayer();
            player.setDataSource(mp3Files.get(++pos).getPath());
            playOrPauseMediaPlayer(context);
            MusicNotification.changeTitle(pos);
            MediaPlayerService.setPosition(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rewindMediaPlayer(Context context, int pos) {
        try {
            if (pos <= 0) {
                pos = 0;
            } else {
                --pos;
            }
            closeMediaPlayer();
            player.setDataSource(mp3Files.get(pos).getPath());
            playOrPauseMediaPlayer(context);
            MusicNotification.changeTitle(pos);
            MediaPlayerService.setPosition(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeMediaPlayer() {
        player.stop();
        player.reset();
    }

//
//    @Override
//    public void onCompletion(MediaPlayer mp) {
//        int pos = MediaPlayerService.getPosition();
//        if (pos < mp3Files.size()) {
//            try {
//                player.reset();
//                player.setDataSource(mp3Files.get(++pos).getPath());
//                player.prepare();
//                player.start();
//            }
//            catch (IOException e){
//                Log.i("TAG", e.toString());
//            }
//        }
//        else
//        {
//            player.release();
//        }
    //}

}
