package com.hangoclong.intent_android;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    static final String PATH = "/DCIM/Camera/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadImageFromGallery();
    }


    private void loadImageFromGallery() {
        RecyclerView recyclerView = findViewById(R.id.recycler_album);
        String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory() .getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + PATH;
        File targetDirector = new File(targetPath);
        File[] files = targetDirector.listFiles();
        ImageAdapter adapter = new ImageAdapter(files);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.grid_layout_margin);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}

