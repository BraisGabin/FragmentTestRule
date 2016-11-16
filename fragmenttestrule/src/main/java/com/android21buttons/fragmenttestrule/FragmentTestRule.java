package com.android21buttons.fragmenttestrule;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * This rule provides functional testing of a single fragment.
 * <p>
 * Idea extracted from: http://stackoverflow.com/a/38393087/842697
 *
 * @param <F> the Fragment to test
 */
public class FragmentTestRule<A extends TestActivity, F extends Fragment> extends ActivityTestRule<A> {
    private static final String TAG = "FragmentTestRule";

    private final Class<F> fragmentClass;
    private F fragment;

    public static <F extends Fragment> FragmentTestRule<TestActivity, F> create(Class<F> fragmentClass) {
        return create(TestActivity.class, fragmentClass);
    }

    public static <F extends Fragment> FragmentTestRule<TestActivity, F> create(Class<F> fragmentClass, boolean initialTouchMode) {
        return create(TestActivity.class, fragmentClass, initialTouchMode);
    }

    public static <F extends Fragment> FragmentTestRule<TestActivity, F> create(Class<F> fragmentClass, boolean initialTouchMode, boolean launchActivity) {
        return create(TestActivity.class, fragmentClass, initialTouchMode, launchActivity);
    }

    public static <A extends TestActivity, F extends Fragment> FragmentTestRule<A, F> create(Class<A> activityClass, Class<F> fragmentClass) {
        return create(activityClass, fragmentClass, false);
    }

    public static <A extends TestActivity, F extends Fragment> FragmentTestRule<A, F> create(Class<A> activityClass, Class<F> fragmentClass, boolean initialTouchMode) {
        return create(activityClass, fragmentClass, initialTouchMode, true);
    }

    public static <A extends TestActivity, F extends Fragment> FragmentTestRule<A, F> create(Class<A> activityClass, Class<F> fragmentClass, boolean initialTouchMode, boolean launchActivity) {
        return new FragmentTestRule<>(activityClass, fragmentClass, initialTouchMode, launchActivity);
    }

    protected FragmentTestRule(Class<A> activityClass, Class<F> fragmentClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
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

    protected F createFragment() {
        try {
            return fragmentClass.newInstance();
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
