package com.android21buttons.fragmenttestrule.sample;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class FragmentWithActivityDependencyTest {

    @Rule
    public FragmentTestRule<DebugActivity, FragmentWithActivityDependency> fragmentTestRule =
            new FragmentTestRule<>(DebugActivity.class, FragmentWithActivityDependency.class);

    @Test
    public void clickButton() throws Exception {
        onView(withText(R.string.button)).perform(click());

        onView(withText("Hello world!")).check(matches(isDisplayed()));
    }
}
