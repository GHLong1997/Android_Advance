package com.hangoclong.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.hangoclong.myapplication.Adapter.RecyclerAdapter;
import com.hangoclong.myapplication.Model.HeroObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerHero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerHero = findViewById(R.id.recycler_hero);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Kata Super Heroes");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        loadHeroList();

    }

    private void loadHeroList() {
        List<HeroObject> heroObjectList = new ArrayList<>();
        String[] nameList = {"Caption America", "Iron Man", "Thor", "The Incredible Hulk",
                "Spider Woman", "Ms.Marvel", "Antman"};
        int[] imageList = {R.drawable.captionamerica, R.drawable.ironman, R.drawable.thor,
                R.drawable.hulk, R.drawable.spiderwoman, R.drawable.msmarve, R.drawable.antman};

        for(int i = 0 ; i < nameList.length; i++) {
            HeroObject heroObject = new HeroObject();
            heroObject.setName(nameList[i]);
            heroObject.setImage(imageList[i]);
            heroObjectList.add(heroObject);
        }

        RecyclerAdapter adapter = new RecyclerAdapter(this, heroObjectList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerHero.setLayoutManager(layoutManager);
        mRecyclerHero.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
