package com.example.giphytask.core

/**
 * @param T - сравниваемый тип данных
 */
interface Comparing<T> {
    fun sameContent(data: T): Boolean
    fun same(data: T): Boolean
}