package com.tmosest.calculator

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule // note rule must be public
    @JvmField
    public var rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun clickingDigitButtonsShouldUpdateInput() {
        onView(withId(R.id.button0)).perform(click())
        onView(withId(R.id.button1)).perform(click())
        onView(withId(R.id.button2)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        onView(withId(R.id.button4)).perform(click())
        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.button6)).perform(click())
        onView(withId(R.id.button7)).perform(click())
        onView(withId(R.id.button8)).perform(click())
        onView(withId(R.id.button9)).perform(click())
        onView(withId(R.id.newNumber)).check(matches(withText("0123456789")))
    }

    @Test
    fun clickingNegationButtonShouldAddNegativeSignThenRemoveIt() {
        onView(withId(R.id.buttonNegate)).perform(click())
        onView(withId(R.id.newNumber)).check(matches(withText("-")))
        onView(withId(R.id.button1)).perform(click())
        onView(withId(R.id.button2)).perform(click())
        onView(withId(R.id.newNumber)).check(matches(withText("-12")))
        onView(withId(R.id.buttonNegate)).perform(click())
        onView(withId(R.id.newNumber)).check(matches(withText("12")))
    }

    @Test
    fun clickingDecimalButtonShouldAddOnlyOneDot() {
        onView(withId(R.id.buttonDot)).perform(click())
        onView(withId(R.id.newNumber)).check(matches(withText(".")))
        onView(withId(R.id.button2)).perform(click())
        onView(withId(R.id.newNumber)).check(matches(withText(".2")))
        onView(withId(R.id.buttonDot)).perform(click())
        onView(withId(R.id.newNumber)).check(matches(withText(".2")))
    }
}
