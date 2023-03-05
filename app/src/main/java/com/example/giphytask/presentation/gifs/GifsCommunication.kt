package com.example.giphytask.presentation.gifs

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.giphytask.presentation.Communication
import com.example.giphytask.presentation.gifs.adapter.GifUi

/**
 * Обертка над лайф датой для экрана списка гифок (GifsFragment)
 */
interface GifsCommunication : Communication.Mutable<List<GifUi>> {

    class Base : GifsCommunication {
        private val gifsLiveData = MutableLiveData<List<GifUi>>()

        override fun map(source: List<GifUi>) {
            gifsLiveData.value = source
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<GifUi>>) {
            gifsLiveData.observe(owner, observer)
        }
    }
}