package com.andalus.mohamedawadtasks;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.andalus.mohamedawadtasks.mediaPlayer.ListOfMp3FromStorage;
import com.andalus.mohamedawadtasks.services.MediaPlayerService;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MusicNotification {

    private static final int NOTIFICATION_REQUEST_CODE = 1997;
    private static final String NOTIFICATION_CHANNEL_ID = "notificationChannel";
    private static final int IGNORE_NOTIFICATION_ID = 2999;
    private static RemoteViews bigView;
    private static RemoteViews smallView;
    private static Notification notification;
    private static NotificationManager notificationManager;

    public static void remindUserWithNotification(Context context, int pos) {
        notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "MUSIC PLAYER",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(notificationChannel);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            buildNotification(context, pos);

        }


    }


    private static Bitmap getAlbumArt(int pos, Context context) {
        try {
            final Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");
            Uri uri = ContentUris.withAppendedId(sArtworkUri,
                    ListOfMp3FromStorage.mp3Files.get(pos).getAlbum());
            ContentResolver res = context.getContentResolver();
            InputStream in = res.openInputStream(uri);
            return BitmapFactory.decodeStream(in);
        } catch (FileNotFoundException e) {
        }
        return null;
    }


    public static Notification buildNotification(Context context, int pos) {

        bigView = new RemoteViews(context.getPackageName(), R.layout.activity_custom_notification);
        bigView.setTextViewText(R.id.titleTextView, ListOfMp3FromStorage.mp3Files.get(pos).getTitle());
        bigView.setOnClickPendingIntent(R.id.closeService, closeNotification(context));
        bigView.setOnClickPendingIntent(R.id.nextButton, forwardNotification(context));
        bigView.setOnClickPendingIntent(R.id.backButton, rewindNotification(context));
        bigView.setOnClickPendingIntent(R.id.playOrPauseButton, playOrPauseNotification(context));

        smallView = new RemoteViews(context.getPackageName(), R.layout.activity_custom_small_notification);
        smallView.setTextViewText(R.id.titleTextView, ListOfMp3FromStorage.mp3Files.get(pos).getTitle());
        smallView.setOnClickPendingIntent(R.id.nextButton, forwardNotification(context));
        smallView.setOnClickPendingIntent(R.id.backButton, rewindNotification(context));
        smallView.setOnClickPendingIntent(R.id.playOrPauseButton, playOrPauseNotification(context));

        if (getAlbumArt(pos, context) == null) {
            bigView.setImageViewResource(R.id.musicImageView, R.drawable.ic_musical_note);
            smallView.setImageViewResource(R.id.musicImageView, R.drawable.ic_musical_note);
        } else {
            smallView.setImageViewBitmap(R.id.musicImageView, getAlbumArt(pos, context));
            bigView.setImageViewBitmap(R.id.musicImageView, getAlbumArt(pos, context));
        }

        notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_musical_white)
                .setContentIntent(contentIntent(context))
                .setCustomBigContentView(bigView)
                .setCustomContentView(smallView)
                .build();

        return notification;
    }

    public static void changeImageToPlay() {
        bigView.setImageViewResource(R.id.playOrPauseButton, R.drawable.ic_play_arrow);

        smallView.setImageViewResource(R.id.playOrPauseButton, R.drawable.ic_play_arrow);
        notification.contentView = bigView;
        notification.contentView = smallView;
        notificationManager.notify(1, notification);
    }

    public static void changeImageToPause() {
        bigView.setImageViewResource(R.id.playOrPauseButton, R.drawable.ic_pause);
        smallView.setImageViewResource(R.id.playOrPauseButton, R.drawable.ic_pause);
        notification.contentView = bigView;
        notification.contentView = smallView;
        notificationManager.notify(1, notification);
    }

    public static void changeTitle(int position) {
        bigView.setTextViewText(R.id.titleTextView, ListOfMp3FromStorage.mp3Files.get(position).getTitle());
        smallView.setTextViewText(R.id.titleTextView, ListOfMp3FromStorage.mp3Files.get(position).getTitle());
        notification.contentView = bigView;
        notification.contentView = smallView;
        notificationManager.notify(1, notification);
    }

    private static PendingIntent closeNotification(Context context) {
        Intent intent = new Intent(context, MediaPlayerService.class);
        intent.setAction(MediaPlayerService.CLOSE_MEDIA_PLAYER);

        return PendingIntent.getService(
                context,
                IGNORE_NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private static PendingIntent playOrPauseNotification(Context context) {
        Intent intent = new Intent(context, MediaPlayerService.class);
        intent.setAction(MediaPlayerService.PLAY_OR_PAUSE_MEDIA_PLAYER);
        MediaPlayerService.setContext(context);
        return PendingIntent.getService(
                context,
                165009,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }


    private static PendingIntent rewindNotification(Context context) {
        Intent intent = new Intent(context, MediaPlayerService.class);
        intent.setAction(MediaPlayerService.REWIND_MEDIA_PLAYER);

        return PendingIntent.getService(
                context,
                IGNORE_NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private static PendingIntent forwardNotification(Context context) {
        Intent intent = new Intent(context, MediaPlayerService.class);
        intent.setAction(MediaPlayerService.FORWARD_MEDIA_PLAYER);

        return PendingIntent.getService(
                context,
                IGNORE_NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }


    private static PendingIntent contentIntent(Context context) {
        Intent intent = new Intent(context, MediaPlayerService.class);

        return PendingIntent.getActivity(
                context,
                NOTIFICATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

}

