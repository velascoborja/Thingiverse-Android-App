package es.borjavg.thingiverse.util

import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf


open class BaseTestRobot {

    fun clickButton(@IdRes resId: Int): ViewInteraction =
        onView((withId(resId))).perform(click())

    fun clickListItem(@IdRes listRes: Int, position: Int) {
        onView(withId(listRes)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                click()
            )
        )
    }

    fun clickListItemChildView(
        @IdRes listRes: Int,
        @IdRes childViewId: Int,
        position: Int
    ) {
        onView(withId(listRes)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                clickChildViewWithId(childViewId)
            )
        )
    }

    fun matchToolbarTitle(@IdRes resId: Int, text: String) {
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(resId))))
            .check(matches(withText(text)))
    }

    fun matchVisible(@IdRes resId: Int) {
        onView(withId(resId)).check(matches(isDisplayed()))
    }

    fun matchRecyclerViewSize(@IdRes listId: Int) {
        onView(withId(listId)).check(RecyclerViewPopulatedAssertion())
    }

    fun matchIntent(action: String) =
        Intents.intended(allOf(IntentMatchers.hasAction(CoreMatchers.equalTo(action))))
}