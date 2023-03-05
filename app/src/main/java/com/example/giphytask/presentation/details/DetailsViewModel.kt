package com.example.giphytask.presentation.details

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.giphytask.data.DetailsRepository

/**
 * Вью модель для фрагмента с деталями гиф
 */
class GifDetailsViewModel(
    private val repository: DetailsRepository,
    private val communication: DetailsCommunication
) : ViewModel(), FetchGifDetails, ObserveGifDetails {

    override fun fetchGifDetails(id: String) {
        repository.fetchGifDetails(id).map(communication)
    }

    override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<DetailsUi>) {
        communication.observe(lifecycleOwner, observer)
    }
}

interface FetchGifDetails {
    /**
     * Получение данных по идентификатору гиф
     */
    fun fetchGifDetails(id: String)
}

interface ObserveGifDetails {
    /**
     * Подписываемся на обновление лайв даты
     */
    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<DetailsUi>)
}