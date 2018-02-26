package com.hangoclong.listenmusicappex;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MusicService extends Service{

    private MediaPlayer mMediaPlayer;
    private int resumePosition;
    private IBinder mIBinder = new LocalBinder();
    private int mSongIndex = 0;
    private int mSongList[] = {R.raw.concobebe, R.raw.backimthang, R.raw.motconvit};
    private String mSongNameList[] = {"Con cò bé bé", "Bắc kim thang", "Một con vịt"};
    private static final int NOTIFICATION_ID = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
        createNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        removeNotification();
        stopSelf();
    }


    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    private void initMediaPlayer() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.concobebe);
        mMediaPlayer.setVolume(100,100);
    }

    public void preSong() {
        mMediaPlayer.stop();
        if (mSongIndex == 0) {
            mSongIndex = mSongList.length - 1;
        }else {
            mSongIndex--;
        }
        mMediaPlayer = MediaPlayer.create(this, mSongList[mSongIndex]);
    }

    public void nextSong() {
        mMediaPlayer.stop();
        if (mSongIndex == mSongList.length - 1) {
            mSongIndex = 0;
        }else {
            mSongIndex++;
        }
        mMediaPlayer = MediaPlayer.create(this, mSongList[mSongIndex]);
    }

    public String getSongName() {
        return mSongNameList[mSongIndex];
    }

    public int getSongDuration() {
        return mMediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public void fastForward(int position) {
        mMediaPlayer.seekTo(position);
    }

    public void playMedia() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    public void pauseMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            resumePosition = mMediaPlayer.getCurrentPosition()*1000;
        }
    }

    public void resumeMedia() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(resumePosition);
            mMediaPlayer.start();
        }
    }

    public String getDurationTimer(){
        final long minutes= (mMediaPlayer.getDuration()/1000)/60;
        final int seconds = (mMediaPlayer.getDuration()/1000)%60;
        return minutes + ":" + seconds;
    }

    private void createNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Music")
                .setContentText("Music is Playing")
                .setSmallIcon(R.drawable.ic_pause_white_24dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private void removeNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null)
        notificationManager.cancel(NOTIFICATION_ID);
    }

}
