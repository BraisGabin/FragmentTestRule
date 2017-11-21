package com.android21buttons.fragmenttestrule.sample;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class FragmentWithoutActivityDependencyTest {

    @Rule
    public FragmentTestRule<?, FragmentWithoutActivityDependency> fragmentTestRule =
            FragmentTestRule.create(FragmentWithoutActivityDependency.class);

    @Test
    public void clickButton() throws Exception {
        onView(withText(R.string.button)).perform(click());

        onView(withText(R.string.button_clicked)).check(matches(isDisplayed()));
    }
}
