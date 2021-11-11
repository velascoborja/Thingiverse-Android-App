package es.borjavg.presentation

import androidx.annotation.StringRes

interface ErrorProvider {

    fun defaultError(
        original: Throwable?,
        displayMode: ErrorDisplayMode = ErrorDisplayMode.FullScreen
    ): PresentationError

    fun defaultError(
        original: Throwable?,
        title: String? = null,
        message: String? = null,
        displayMode: ErrorDisplayMode = ErrorDisplayMode.FullScreen
    ): PresentationError

    fun defaultError(
        original: Throwable?,
        @StringRes titleRes: Int? = null,
        @StringRes messageRes: Int? = null,
        displayMode: ErrorDisplayMode = ErrorDisplayMode.FullScreen
    ): PresentationError

    fun popBackError(
        original: Throwable?,
        @StringRes title: Int? = null,
        @StringRes message: Int? = null,
        displayMode: ErrorDisplayMode = ErrorDisplayMode.FullScreen
    ): PresentationError

    fun recoverableError(
        original: Throwable?,
        @StringRes title: Int? = null,
        @StringRes message: Int? = null,
        displayMode: ErrorDisplayMode = ErrorDisplayMode.FullScreen,
        action: () -> Unit
    ): PresentationError
}

enum class ErrorDisplayMode {
    FullScreen,
    Dialog
}

interface PresentationError {
    val title: String
    val message: String
    val displayMode: ErrorDisplayMode
}

class Error(
    override val title: String,
    override val message: String,
    override val displayMode: ErrorDisplayMode = ErrorDisplayMode.FullScreen
) : PresentationError

class PopBackError(
    override val title: String,
    override val message: String,
    override val displayMode: ErrorDisplayMode = ErrorDisplayMode.FullScreen
) : PresentationError

class RecoverableError(
    override val title: String,
    override val message: String,
    override val displayMode: ErrorDisplayMode = ErrorDisplayMode.FullScreen,
    val action: () -> Unit
) : PresentationError
