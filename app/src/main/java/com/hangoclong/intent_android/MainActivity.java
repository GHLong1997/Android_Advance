package com.hangoclong.intent_android;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageAdapter mImageAdapter = new ImageAdapter();
    static final String PATH = "/DCIM/Camera/";
    private File[] files;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadImageFromGallery();
    }

    private void loadImage() {
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (message.what != -1) {
                    mImageAdapter.add(files[message.what]);
                    mImageAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        Thread thread = new Thread() {
            public void run() {
                getPath();
                if (files != null) {
                    for (int i = 0; i< files.length; i++) {
                        handler.sendEmptyMessage(i);
                    }
                }
            }
        };
        thread.start();
    }

    private void getPath() {
        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory().getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + PATH;
        File mTargetDirector = new File(targetPath);
        files = mTargetDirector.listFiles();
    }
    private void loadImageFromGallery() {
        RecyclerView recyclerView = findViewById(R.id.recycler_album);
        loadImage();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.grid_layout_margin);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mImageAdapter);
    }

}

