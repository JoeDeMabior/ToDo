package com.joey.todo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.joey.todo.ui.NewTaskActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

@RunWith(AndroidJUnit4::class)
@LargeTest
class NewTaskActivityTest {

    @get:Rule
    var newTaskRule = ActivityTestRule(NewTaskActivity::class.java)

    @Test
    fun checkViewDisplay() {
        onView(withText(R.string.create_a_new_task)).check(matches(isDisplayed()))
        onView(withText(R.string.title)).check(matches(isDisplayed()))
        onView(withId(R.id.taskName)).check(matches(isDisplayed()))
        onView(withText(R.string.description)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withText(R.string.deadline)).check(matches(isDisplayed()))
        onView(withId(R.id.date)).check(matches(isDisplayed()))
        onView(withId(R.id.radio)).check(matches(isDisplayed()))
        onView(withId(R.id.create)).check(matches(isDisplayed()))
    }
}
