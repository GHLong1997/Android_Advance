package com.hangoclong.tablayoutandviewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.hangoclong.tablayoutandviewpager.Adapter.ViewPagerAdapter;
import com.hangoclong.tablayoutandviewpager.Fragment.Fragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    static final String BUNDLE_CONTENT = "BUNDLE_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = findViewById(R.id.tab_layout);
        loadViewPager();
        setupTabIcons();
        hideStatusBar();
    }

    private void loadViewPager() {
        ViewPager mViewPager = findViewById(R.id.view_pager);
        String[] content = {"First Fragment", "Second Fragment", "Third Fragment"};
        String[] title = {"First", "Second", "Third"};
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_CONTENT, content[i]);
            Fragment fragment = new Fragment();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, title);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void setupTabIcons() {
        for(int i = 0; i < mTabLayout.getTabCount(); i++){
            TabLayout.Tab x = mTabLayout.getTabAt(i);
            if(x != null) {
                x.setIcon(R.drawable.ic_android);
            }
        }
    }

}
