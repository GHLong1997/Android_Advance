package com.hangoclong.listenmusicappex;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private TextView mTextViewMaxSong, mTextViewSongProgress, mTextViewSongName;
    private SeekBar mSeekBar;
    private ImageButton mImageButtonNextSong, mImageButtonPreSong;
    private MusicService mMusicService;
    private ImageButton mImageButtonPlayMusic;
    private ServiceConnection mServiceConnection;
    private Handler mHandler = new Handler();
    private boolean mIsPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        playService();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_play:
                if (!mIsPause) {
                    mMusicService.playMedia();
                    mImageButtonPlayMusic.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_white_24dp));
                    mIsPause = true;
                }else {
                    mMusicService.pauseMedia();
                    mImageButtonPlayMusic.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_white_24dp));
                    mIsPause = false;
                }
                updateProgressBar();
                break;
            case R.id.image_next_song:
                mMusicService.nextSong();
                setupView();
                break;
            case R.id.image_pre_song:
                mMusicService.preSong();
                setupView();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mMusicService.fastForward(i*1000);
        long mMinutes=(i/60);
        int mSeconds=(i%60);
        String time = mMinutes + ":" + mSeconds;
        mTextViewSongProgress.setText(time);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void initView() {
        mSeekBar = findViewById(R.id.seek_bar);
        mTextViewSongName = findViewById(R.id.text_songName);
        mTextViewSongProgress = findViewById(R.id.text_song_progress);
        mTextViewMaxSong = findViewById(R.id.textView_maxSongTime);
        mImageButtonNextSong = findViewById(R.id.image_next_song);
        mImageButtonPreSong = findViewById(R.id.image_pre_song);
        mImageButtonPlayMusic = findViewById(R.id.image_play);
        mImageButtonPlayMusic.setOnClickListener(this);
        mImageButtonNextSong.setOnClickListener(this);
        mImageButtonPreSong.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    public  void playService() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getService();
                playAudio();
            }
        });
        thread.start();
    }

    private void getService() {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MusicService.LocalBinder binder = (MusicService.LocalBinder) iBinder;
                mMusicService = binder.getService();
                mTextViewSongName.setText(mMusicService.getSongName());
                mSeekBar.setMax(mMusicService.getSongDuration()/1000);
                mSeekBar.setProgress(mMusicService.getCurrentPosition()/1000);
                mTextViewMaxSong.setText(mMusicService.getDurationTimer());
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {}
        };
    }

    private void playAudio() {
        Intent playerIntent = new Intent(MainActivity.this, MusicService.class);
        startService(playerIntent);
        bindService(playerIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void updateProgressBar() {
        if (mMusicService.getCurrentPosition()/1000 < mMusicService.getSongDuration()/1000) {
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    mSeekBar.setProgress(mMusicService.getCurrentPosition() / 1000);
                    mHandler.postDelayed(this, 1000);
                    if ((mMusicService.getCurrentPosition()/1000 == mMusicService.getSongDuration()/1000)) {
                        mMusicService.nextSong();
                        setupView();
                        mMusicService.playMedia();
                        updateProgressBar();
                    }
                }
            };
            runnable.run();
        }
    }

    private void setupView() {
        mTextViewSongName.setText(mMusicService.getSongName());
        mTextViewMaxSong.setText(mMusicService.getDurationTimer());
        mSeekBar.setMax(mMusicService.getSongDuration()/1000);
        mSeekBar.setProgress(0);
        String time = 0 + ":" + 0;
        mTextViewSongProgress.setText(time);
        mIsPause = false;
        mImageButtonPlayMusic.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_white_24dp));
    }
}
