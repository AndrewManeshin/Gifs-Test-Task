package com.example.giphytask.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.giphytask.core.Mapper

/**
 * Абстрактный интерфейс для создания обертки над лайв датой
 */
interface Communication {

    interface Observe<T> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>)
    }

    interface Mutate<T> : Mapper.Unit<T>
    interface Mutable<T> : Observe<T>, Mutate<T>
}