package com.hangoclong.tablayoutandviewpager.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hangoclong.tablayoutandviewpager.R;


public class Fragment extends android.support.v4.app.Fragment {

    static final String BUNDLE_CONTENT = "BUNDLE_CONTENT";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment, container, false);
        TextView textView = view.findViewById(R.id.text_view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            textView.setText(bundle.getString(BUNDLE_CONTENT,""));
        }
        return view;
    }

}
