package com.android21buttons.fragmenttestrule;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * This rule provides functional testing of a single fragment.
 *
 * Idea extracted from: http://stackoverflow.com/a/38393087/842697
 *
 * @param <F> the Fragment to test
 */
public class FragmentTestRule<F extends Fragment> extends ActivityTestRule<TestActivity> {
    private static final String TAG = "FragmentTestRule";

    private final Class<F> fragmentClass;
    private F fragment;

    public FragmentTestRule(Class<F> fragmentClass) {
        this(fragmentClass, false);
    }

    public FragmentTestRule(Class<F> fragmentClass, boolean initialTouchMode) {
        this(fragmentClass, initialTouchMode, true);
    }

    public FragmentTestRule(Class<F> fragmentClass, boolean initialTouchMode, boolean launchActivity) {
        super(TestActivity.class, initialTouchMode, launchActivity);
        this.fragmentClass = fragmentClass;
    }

    @Override
    protected void afterActivityLaunched() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentTestRule.this.fragment = createFragment();
                getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentTestRule_fForFragment, fragment)
                    .commit();
            }
        });
    }

    @SuppressWarnings("unchecked")
    protected F createFragment() {
        try {
            return (F) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            throw new AssertionError(String.format("%s: Could not insert %s into %s: %s",
                getClass().getSimpleName(),
                fragmentClass.getSimpleName(),
                TestActivity.class.getSimpleName(),
                e.getMessage()));
        } catch (IllegalAccessException e) {
            throw new AssertionError(String.format("%s: Could not insert %s into %s: %s",
                getClass().getSimpleName(),
                fragmentClass.getSimpleName(),
                TestActivity.class.getSimpleName(),
                e.getMessage()));
        }
    }


    public F getFragment() {
        if (fragment == null) {
            Log.w(TAG, "Fragment wasn't created yet");
        }
        return fragment;
    }
}
