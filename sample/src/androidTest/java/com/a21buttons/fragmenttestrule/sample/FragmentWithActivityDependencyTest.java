package com.a21buttons.fragmenttestrule.sample;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
