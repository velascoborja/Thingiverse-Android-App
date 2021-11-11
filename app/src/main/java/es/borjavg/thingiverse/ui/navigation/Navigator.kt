package es.borjavg.thingiverse.ui.navigation

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import es.borjavg.thingiverse.R
import java.lang.ref.WeakReference
import java.util.Stack
import kotlin.reflect.KClass

interface NavigatorOwner {
    val navigator: Navigator
}

interface Navigator {
    var stackCountListener: (Int) -> Unit
    val currentFragment: Fragment?
    fun push(fragment: Fragment)
    fun replace(fragment: Fragment)
    fun pop(): Boolean
    fun clearBackStack()
    fun <T : Fragment> popTo(kClass: KClass<T>)
    val isAtRoot: Boolean
}

class NavigatorAnimations(
    @AnimRes @AnimatorRes val enter: Int = 0,
    @AnimRes @AnimatorRes val exit: Int = 0,
    @AnimRes @AnimatorRes val popEnter: Int = 0,
    @AnimRes @AnimatorRes val popExit: Int = 0
) {
    companion object {
        val Fade = NavigatorAnimations(
            enter = R.anim.fade_in,
            exit = R.anim.fade_out,
            popEnter = R.anim.fade_in,
            popExit = R.anim.fade_out
        )

        val SlideHorizontal = NavigatorAnimations(
            enter = R.anim.slide_in_right,
            exit = R.anim.slide_out_left,
            popEnter = R.anim.slide_in_left,
            popExit = R.anim.slide_out_right
        )
    }
}

class NavigatorImpl(
    private val fragmentManager: FragmentManager,
    private val container: Int,
    private val animations: NavigatorAnimations = NavigatorAnimations.Fade
) : Navigator {

    override var stackCountListener: (Int) -> Unit = {}

    private var fragmentStack = Stack<WeakReference<Fragment>>()

    override val currentFragment: Fragment? get() = fragmentStack.safePeek()?.get()

    init {
        fragmentManager.addOnBackStackChangedListener {
            stackCountListener(fragmentManager.backStackEntryCount)
        }
    }

    override fun push(fragment: Fragment) {
        fragmentStack.push(WeakReference(fragment))
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                animations.enter,
                animations.exit,
                animations.popEnter,
                animations.popExit
            )
            .replace(container, fragment)
            .addToBackStack(fragment::class.qualifiedName)
            .commit()

        fragmentManager.executePendingTransactions()
    }

    override fun replace(fragment: Fragment) {
        pop()
        push(fragment)
    }

    override fun pop(): Boolean {
        fragmentStack.safePop()
        return fragmentManager.popBackStackImmediate()
    }

    override fun clearBackStack() {
        if (fragmentManager.backStackEntryCount > 0) {
            val e = fragmentManager.getBackStackEntryAt(0)
            fragmentManager.popBackStackImmediate(e.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            fragmentStack.safePopAll()
        }
    }

    override fun <T : Fragment> popTo(kClass: KClass<T>) {
        val getCurrentFragmentClass = { fragmentStack.safePeek()?.get()?.javaClass }
        while (fragmentStack.isNotEmpty() && getCurrentFragmentClass() != kClass.java) {
            fragmentStack.safePop()
        }
        fragmentManager.popBackStackImmediate(kClass.qualifiedName, 0)
    }

    override val isAtRoot: Boolean
        get() = fragmentManager.backStackEntryCount <= 1

    private fun <T> Stack<T>.safePop(): T? = if (!empty()) pop() else null

    private fun <T> Stack<T>.safePeek(): T? = if (!empty()) peek() else null

    private fun <T> Stack<T>.safePopAll() {
        while (!empty()) {
            safePop()
        }
    }
}

interface OnBackPressedHandler {

    fun onBackPressed(onContinue: () -> Unit)
}
