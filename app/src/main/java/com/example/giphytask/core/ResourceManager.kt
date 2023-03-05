package com.example.giphytask.core

import android.content.Context
import androidx.annotation.StringRes

/**
 * Класс обертка над контекстом для доступа к ресурсам
 */
interface ResourceManager {
    fun string(@StringRes resId: Int): String

    class Base(private val context: Context) : ResourceManager {
        override fun string(resId: Int) = context.getString(resId)
    }
}