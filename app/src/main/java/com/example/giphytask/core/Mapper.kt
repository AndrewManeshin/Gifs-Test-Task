package com.example.giphytask.core

/**
 * Маппер для преобразования одних данных к другим
 *
 * @param R result целевой тип данных
 * @param S source исходный тип данных
 **/
interface Mapper<R, S> {

    fun map(source: S): R

    interface Unit<T> : Mapper<kotlin.Unit, T>

    interface Ui<R, S> : Mapper<R, S> {
        fun mapError(source: S, message: String): R
    }
}