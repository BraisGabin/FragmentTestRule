package com.android21buttons.fragmenttestrule.sample;

import androidx.fragment.app.FragmentActivity;

public class DebugActivity extends FragmentActivity implements FragmentWithActivityDependency.TextInterface {
    @Override
    public String getText() {
        return "Hello world!";
    }
}
