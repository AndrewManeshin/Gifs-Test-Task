package com.example.giphytask.presentation.details

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.giphytask.presentation.Communication

/**
 * Класс обертка над liveData
 */
interface DetailsCommunication : Communication.Mutable<DetailsUi> {

    class Base : DetailsCommunication {
        private val detailsLiveData = MediatorLiveData<DetailsUi>()

        override fun map(source: DetailsUi) {
            detailsLiveData.value = source
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<DetailsUi>) {
            detailsLiveData.observe(owner, observer)
        }
    }
}