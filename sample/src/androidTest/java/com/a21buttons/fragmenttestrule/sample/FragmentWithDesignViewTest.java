package com.a21buttons.fragmenttestrule.sample;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.junit.Rule;
import org.junit.Test;

public class FragmentWithDesignViewTest {

    @Rule
    public FragmentTestRule<?, FragmentWithDesignView> fragmentTestRule =
            FragmentTestRule.create(FragmentWithDesignView.class);

    @Test
    public void checkThatItsLaunched() throws Exception {
        // no-op
    }
}
