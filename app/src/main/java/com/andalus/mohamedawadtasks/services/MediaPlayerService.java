package com.andalus.mohamedawadtasks.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.andalus.mohamedawadtasks.MusicNotification;
import com.andalus.mohamedawadtasks.mediaPlayer.ListOfMp3FromStorage;

public class MediaPlayerService extends Service {

    static int pos;
    public static final String RUN_MEDIA_PLAYER = "runIt";
    public static final String CLOSE_MEDIA_PLAYER = "closeIt";
    public static final String PLAY_OR_PAUSE_MEDIA_PLAYER = "pauseIt";
    public static final String FORWARD_MEDIA_PLAYER = "forwardIt";
    public static final String REWIND_MEDIA_PLAYER = "rewindIt";
    private static Context mContext;

    public static void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        switch (action) {
            case RUN_MEDIA_PLAYER:
                MusicNotification.remindUserWithNotification(this, pos);
                startForeground(1, MusicNotification.buildNotification(this, pos));
                ListOfMp3FromStorage.runMediaPlayer(mContext,pos);
                break;
            case CLOSE_MEDIA_PLAYER:
                stopSelf();
                ListOfMp3FromStorage.closeMediaPlayer();
                break;
            case PLAY_OR_PAUSE_MEDIA_PLAYER:
                ListOfMp3FromStorage.playOrPauseMediaPlayer(mContext);
                break;
            case FORWARD_MEDIA_PLAYER:
                MusicNotification.remindUserWithNotification(this, pos);
                startForeground(1, MusicNotification.buildNotification(this, pos));
                ListOfMp3FromStorage.forwardMediaPlayer(mContext,pos);
                break;
            case REWIND_MEDIA_PLAYER:
                MusicNotification.remindUserWithNotification(this, pos);
                startForeground(1, MusicNotification.buildNotification(this, pos));
                ListOfMp3FromStorage.rewindMediaPlayer(mContext,pos);

        }
        return START_NOT_STICKY;
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public static void setPosition(int i){
        pos = i;
    }

}



