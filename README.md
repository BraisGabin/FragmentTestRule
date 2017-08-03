# Fragment Test Rule

Test `Fragment`s in isolation.

## Download

```gradle
compile 'com.21buttons:fragment-test-rule:0.1.0'
```

## Usage

```java
@Rule
public FragmentTestRule<?, FragmentWithoutActivityDependency> fragmentTestRule =
    FragmentTestRule.create(FragmentWithoutActivityDependency.class);

@Test
public void clickButton() throws Exception {
  onView(withText(R.string.button)).perform(click());

  onView(withText(R.string.button_clicked)).check(matches(isDisplayed()));
}
```

You can check the sample code for more examples.
