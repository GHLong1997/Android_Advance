package com.hangoclong.intent_android;

import android.os.AsyncTask;
import android.os.Environment;
import java.io.File;

public class LoadImage extends AsyncTask<Void, File, Void> {

    private File mTargetDirector;
    private ImageAdapter mImageAdapter;
    static final String PATH = "/DCIM/Camera/";

    public LoadImage(ImageAdapter adapter) {
        mImageAdapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory().getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + PATH;
        mTargetDirector = new File(targetPath);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        File[] files = mTargetDirector.listFiles();
        if (files != null) {
            for (File file : files) {
                publishProgress(file);
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(File... values) {
        super.onProgressUpdate(values);
        mImageAdapter.add(values[0]);
        mImageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

}
