package com.example.giphytask.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Обертка над CoroutineDispatcher для возможности подмены в тестах
 */
interface DispatchersList {
    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher

    class Base : DispatchersList {
        override fun io(): CoroutineDispatcher = Dispatchers.IO
        override fun ui(): CoroutineDispatcher = Dispatchers.Main
    }
}