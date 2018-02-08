package com.hangoclong.myapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hangoclong.myapplication.Model.HeroObject;
import com.hangoclong.myapplication.R;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<HeroObject> mHeroObjectList;

    public RecyclerAdapter(Context context, List<HeroObject> heroObjectList) {
        mContext = context;
        mHeroObjectList = heroObjectList;
    }
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        HeroObject heroObject = mHeroObjectList.get(position);
        Glide.with(mContext).load(heroObject.getImage()).into(holder.imageHero);
        holder.nameHero.setText(heroObject.getName());

    }

    @Override
    public int getItemCount() {
        return mHeroObjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameHero;
        ImageView imageHero;
        public ViewHolder(View itemView) {
            super(itemView);
            nameHero = itemView.findViewById(R.id.name_hero);
            imageHero = itemView.findViewById(R.id.image_hero);
        }
    }
}
