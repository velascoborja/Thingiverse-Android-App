package es.borjavg.presentation

import androidx.annotation.PluralsRes
import javax.inject.Inject

/**
 * Abstraction to provide Android strings resources
 */
interface StringsProvider {

    operator fun invoke(stringResId: Int, vararg format: Any): String

    fun plural(@PluralsRes res: Int, quantity: Int, vararg formats: Any): String
}

class StringsProviderImpl @Inject constructor(
    private val activityProvider: CurrentActivityProvider
) : StringsProvider {

    override fun invoke(stringResId: Int, vararg format: Any): String =
        activityProvider.activity?.getString(stringResId, *format).orEmpty()

    override fun plural(res: Int, quantity: Int, vararg formats: Any) =
        activityProvider.activity?.resources?.getQuantityString(res, quantity, *formats).orEmpty()
}
