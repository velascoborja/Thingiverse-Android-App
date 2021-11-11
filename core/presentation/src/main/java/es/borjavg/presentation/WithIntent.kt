@file:Suppress("FunctionName")

package es.borjavg.presentation

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityOptionsCompat

interface WithIntent {

    fun getIntent(context: Context): Intent
}

interface WithResult<O> {

    fun getResult(resultCode: Int, intent: Intent?): O?
}

interface WithArgsIntent<T> {

    fun getIntent(context: Context, args: T): Intent
}

fun <I, O> ResultContract(
    getIntent: (Context, I) -> Intent,
    getResult: (Int, Intent?) -> O
) = object : ActivityResultContract<I, O>() {

    override fun createIntent(context: Context, input: I): Intent = getIntent(context, input)

    override fun parseResult(resultCode: Int, intent: Intent?): O = getResult(resultCode, intent)
}

interface ResultContract<O : Any> : WithIntent, WithResult<O> {

    fun getResultContract(): ActivityResultContract<Unit, O?> = ResultContract(
        getIntent = { c, _ -> getIntent(c) },
        getResult = ::getResult
    )
}

interface ResultContractWithArgs<I, O : Any> : WithArgsIntent<I>, WithResult<O> {

    fun getResultContract(): ActivityResultContract<I, O?> = ResultContract(
        getIntent = { c, i -> getIntent(c, i) },
        getResult = ::getResult
    )
}

fun ActivityResultLauncher<Unit>.launch(options: ActivityOptionsCompat) = launch(Unit, options)
