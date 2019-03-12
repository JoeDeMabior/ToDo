package com.joey.todo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.joey.todo.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var mainRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun checkViewDisplay() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
        onView(withId(R.id.titleView)).check(matches(isDisplayed()));
        onView(withId(R.id.descView)).check(matches(isDisplayed()));
        onView(withId(R.id.dateView)).check(matches(isDisplayed()));
    }
}