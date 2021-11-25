package es.borjavg.thingiverse.util

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import org.junit.Assert

fun clickChildViewWithId(@IdRes id: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints() = null

        override fun getDescription() = "Click on a child view with specified id."

        override fun perform(uiController: UiController?, view: View?) {
            view?.findViewById<View>(id)?.performClick()
        }
    }
}

class RecyclerViewPopulatedAssertion() : ViewAssertion {
    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        Assert.assertTrue(adapter?.itemCount != 0)
    }
}