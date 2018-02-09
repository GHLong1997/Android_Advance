package com.hangoclong.intent_android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.io.File;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private File[] mFiles;

    public ImageAdapter(File[] files) {
        this.mFiles = files;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (inflater != null) {
            return new ViewHolder(inflater.inflate(R.layout.custom_recycler, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.fillData(mFiles[position]);
    }

    @Override
    public int getItemCount() {
        if (mFiles != null) {
            return mFiles.length;
        }
        return 0;
    }

     static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

         ViewHolder(View itemView) {
            super(itemView);
             mImageView = itemView.findViewById(R.id.image);
        }

         void fillData(File file) {
            Glide.with(itemView.getContext()).load(file).into(mImageView);
        }
    }

}
