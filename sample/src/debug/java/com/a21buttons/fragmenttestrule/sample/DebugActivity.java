package com.a21buttons.fragmenttestrule.sample;

import android.support.v4.app.FragmentActivity;

public class DebugActivity extends FragmentActivity implements FragmentWithActivityDependency.TextInterface {
    @Override
    public String getText() {
        return "Hello world!";
    }
}
