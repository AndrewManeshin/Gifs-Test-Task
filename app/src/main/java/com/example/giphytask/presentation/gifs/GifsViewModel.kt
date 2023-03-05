package com.example.giphytask.presentation.gifs

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphytask.core.DispatchersList
import com.example.giphytask.data.GifsRepository
import com.example.giphytask.presentation.gifs.adapter.GifUi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Вью модель для экрана списка гифок
 */
class GifsViewModel(
    private val repository: GifsRepository,
    private val communication: GifsCommunication,
    private val dispatchers: DispatchersList
) : ViewModel(), FetchGifs, ObserveGifs, Init {

    private var lastVisibleItemPos = -1

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            GifsUi(listOf(GifUi.FullSizeLoader)).map(communication)
            fetchGifs()
        }
    }

    override fun observeGifs(owner: LifecycleOwner, observer: Observer<List<GifUi>>) {
        communication.observe(owner, observer)
    }

    override fun fetchGifs() {
        viewModelScope.launch(dispatchers.io()) {
            val result = repository.fetchGifs("")
            withContext(dispatchers.ui()) {
                result.map(communication)
            }
        }
    }

    override fun fetchMoreGifs(lastVisibleItemPosition: Int) {
        if (lastVisibleItemPosition != lastVisibleItemPos) {
            if (repository.needToLoadMoreData(lastVisibleItemPosition)) {
                lastVisibleItemPos = lastVisibleItemPosition
                fetchGifs()
            }
        }
    }

    override fun fetchGifs(searchWord: String) {
        GifsUi(listOf(GifUi.FullSizeLoader)).map(communication)
        viewModelScope.launch(dispatchers.io()) {
            val result = repository.fetchGifs(searchWord)
            withContext(dispatchers.ui()) {
                result.map(communication)
            }
        }
    }
}

interface Init {
    /**
     * Отображаем начальное состояние экрана
     */
    fun init(isFirstRun: Boolean)
}

interface FetchGifs {
    /**
     * Получаем данные по поисковому запросу
     */
    fun fetchGifs(searchWord: String)

    /**
     * Получаем данные
     */
    fun fetchGifs()

    /**
     * Нужно ли загружать еще данные
     * Проверяем что юзер проскролил до дна списка
     */
    fun fetchMoreGifs(lastVisibleItemPosition: Int)
}

/**
 * Интерфейс с методами подписки на обновление данных в лайв дате
 */
interface ObserveGifs {
    fun observeGifs(owner: LifecycleOwner, observer: Observer<List<GifUi>>)
}