package com.android21buttons.fragmenttestrule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FragmentTestRuleTest {

    @Test
    public void createFragment() throws Exception {
        FragmentTestRule<?, Fragment> testRule = new FragmentTestRule<>(FragmentActivity.class, Fragment.class);

        assertThat(testRule.createFragment(), is(notNullValue()));
    }

    @Test
    public void getFragmentNoCreate() throws Exception {
        FragmentTestRule<?, Fragment> testRule = new FragmentTestRule<>(FragmentActivity.class, Fragment.class);

        assertThat(testRule.getFragment(), is(nullValue()));
    }
}
