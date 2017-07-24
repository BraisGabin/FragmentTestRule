package com.android21buttons.fragmenttestrule;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * This rule provides functional testing of a single fragment.
 * <p>
 * Idea extracted from: http://stackoverflow.com/a/38393087/842697
 *
 * @param <A> The activity where the fragment will be added
 * @param <F> The fragment to test
 */
public class FragmentTestRule<A extends FragmentActivity, F extends Fragment> extends ActivityTestRule<A> {
    private static final String TAG = "FragmentTestRule";

    private final Class<F> fragmentClass;
    private final boolean launchFragment;
    private F fragment;

    public FragmentTestRule(Class<A> activityClass, Class<F> fragmentClass) {
        this(activityClass, fragmentClass, false);
    }

    public FragmentTestRule(Class<A> activityClass, Class<F> fragmentClass, boolean initialTouchMode) {
        this(activityClass, fragmentClass, initialTouchMode, true);
    }

    public FragmentTestRule(Class<A> activityClass, Class<F> fragmentClass, boolean initialTouchMode, boolean launchActivity) {
        this(activityClass, fragmentClass, initialTouchMode, launchActivity, true);
    }

    public FragmentTestRule(Class<A> activityClass, Class<F> fragmentClass, boolean initialTouchMode, boolean launchActivity, boolean launchFragment) {
        super(activityClass, initialTouchMode, launchActivity);
        this.fragmentClass = fragmentClass;
        this.launchFragment = launchFragment;
    }

    @Override
    protected void afterActivityLaunched() {
        if (launchFragment) {
            launchFragment(createFragment());
        }
    }

    /**
     * Launches the Fragment under test.
     * <p>
     * Don't call this method directly, unless you explicitly requested not to lazily launch the
     * Fragment manually using the launchFragment flag in
     * {@link FragmentTestRule#FragmentTestRule(Class, Class, boolean, boolean, boolean)}.
     * <p>
     * Usage:
     * <pre>
     *    &#064;Test
     *    public void customIntentToStartActivity() {
     *        Fragment fragment = MyFragment.newInstance();
     *        fragmentRule.launchFragment(fragment);
     *    }
     * </pre>
     * @param fragment The Fragment under test. If {@code fragment} is null, the fragment returned
     *                 by {@link FragmentTestRule#createFragment()} is used.
     * @see FragmentTestRule#createFragment()
     */
    public void launchFragment(final F fragment) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final F fragment2 = fragment == null ? createFragment() : fragment;
                    FragmentTestRule.this.fragment = fragment2;
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(android.R.id.content, fragment2)
                            .commitNow();
                }
            });
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    protected F createFragment() {
        try {
            return fragmentClass.newInstance();
        } catch (InstantiationException e) {
            throw new AssertionError(String.format("%s: Could not insert %s into %s: %s",
                    getClass().getSimpleName(),
                    fragmentClass.getSimpleName(),
                    getActivity().getClass().getSimpleName(),
                    e.getMessage()));
        } catch (IllegalAccessException e) {
            throw new AssertionError(String.format("%s: Could not insert %s into %s: %s",
                    getClass().getSimpleName(),
                    fragmentClass.getSimpleName(),
                    getActivity().getClass().getSimpleName(),
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
