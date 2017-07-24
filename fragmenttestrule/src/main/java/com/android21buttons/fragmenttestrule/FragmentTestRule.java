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

    /**
     * Similar to {@link #FragmentTestRule(Class, Class, boolean)} but with "touch mode" disabled.
     *
     * @param activityClass    The activity under test. This must be a class in the instrumentation
     *                         targetPackage specified in the AndroidManifest.xml
     * @param fragmentClass    The fragment under test.
     * @see FragmentTestRule#FragmentTestRule(Class, Class, boolean)
     */
    public FragmentTestRule(Class<A> activityClass, Class<F> fragmentClass) {
        this(activityClass, fragmentClass, false);
    }

    /**
     * Similar to {@link #FragmentTestRule(Class, Class, boolean, boolean)} but defaults to launch
     * the activity under test once per
     * <a href="http://junit.org/javadoc/latest/org/junit/Test.html"><code>Test</code></a> method.
     * It is launched before the first
     * <a href="http://junit.sourceforge.net/javadoc/org/junit/Before.html"><code>Before</code></a>
     * method, and terminated after the last
     * <a href="http://junit.sourceforge.net/javadoc/org/junit/After.html"><code>After</code></a>
     * method.
     *
     * @param activityClass    The activity under test. This must be a class in the instrumentation
     *                         targetPackage specified in the AndroidManifest.xml
     * @param fragmentClass    The fragment under test.
     * @param initialTouchMode true if the Activity should be placed into "touch mode" when started
     * @see FragmentTestRule#FragmentTestRule(Class, Class, boolean, boolean)
     */
    public FragmentTestRule(Class<A> activityClass, Class<F> fragmentClass, boolean initialTouchMode) {
        this(activityClass, fragmentClass, initialTouchMode, true);
    }

    /**
     * Similar to {@link #FragmentTestRule(Class, Class, boolean, boolean, boolean)} but defaults to
     * launch the fragment under test once per
     * <a href="http://junit.org/javadoc/latest/org/junit/Test.html"><code>Test</code></a> method.
     * It is launched before the first
     * <a href="http://junit.sourceforge.net/javadoc/org/junit/Before.html"><code>Before</code></a>
     * method, and terminated after the last
     * <a href="http://junit.sourceforge.net/javadoc/org/junit/After.html"><code>After</code></a>
     * method.
     *
     * @param activityClass    The activity under test. This must be a class in the instrumentation
     *                         targetPackage specified in the AndroidManifest.xml
     * @param fragmentClass    The fragment under test.
     * @param initialTouchMode true if the Activity should be placed into "touch mode" when started
     * @param launchActivity   true if the Activity should be launched once per
     *                         <a href="http://junit.org/javadoc/latest/org/junit/Test.html">
     *                         <code>Test</code></a> method. It will be launched before the first
     *                         <a href="http://junit.sourceforge.net/javadoc/org/junit/Before.html">
     *                         <code>Before</code></a> method, and terminated after the last
     *                         <a href="http://junit.sourceforge.net/javadoc/org/junit/After.html">
     *                         <code>After</code></a> method.
     * @see FragmentTestRule#FragmentTestRule(Class, Class, boolean, boolean, boolean)
     */
    public FragmentTestRule(Class<A> activityClass, Class<F> fragmentClass, boolean initialTouchMode, boolean launchActivity) {
        this(activityClass, fragmentClass, initialTouchMode, launchActivity, true);
    }

    /**
     * Creates an {@link FragmentTestRule} for the Fragment under test.
     *
     * @param activityClass    The activity under test. This must be a class in the instrumentation
     *                         targetPackage specified in the AndroidManifest.xml
     * @param fragmentClass    The fragment under test.
     * @param initialTouchMode true if the Activity should be placed into "touch mode" when started
     * @param launchActivity   true if the Activity should be launched once per
     *                         <a href="http://junit.org/javadoc/latest/org/junit/Test.html">
     *                         <code>Test</code></a> method. It will be launched before the first
     *                         <a href="http://junit.sourceforge.net/javadoc/org/junit/Before.html">
     *                         <code>Before</code></a> method, and terminated after the last
     *                         <a href="http://junit.sourceforge.net/javadoc/org/junit/After.html">
     *                         <code>After</code></a> method.
     * @param launchFragment   true if the Fragment should be launched once per
     *                         <a href="http://junit.org/javadoc/latest/org/junit/Test.html">
     *                         <code>Test</code></a> method. It will be launched before the first
     *                         <a href="http://junit.sourceforge.net/javadoc/org/junit/Before.html">
     *                         <code>Before</code></a> method, and terminated after the last
     *                         <a href="http://junit.sourceforge.net/javadoc/org/junit/After.html">
     *                         <code>After</code></a> method.
     */
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

    /**
     * Override this method to set up the desired Fragment.
     * <p>
     * The default Fragment (if this method returns null or is not overwritten) is:
     * fragmentClass.newInstance()
     *
     * @return the fragment to test.
     */
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

    /**
     * @return The fragment under test.
     */
    public F getFragment() {
        if (fragment == null) {
            Log.w(TAG, "Fragment wasn't created yet");
        }
        return fragment;
    }
}
