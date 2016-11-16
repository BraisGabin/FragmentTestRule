package com.android21buttons.fragmenttestrule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

public class TestActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.fragmentTestRule_fForFragment);
        setContentView(frameLayout);
    }
}
