package com.example.giphytask.presentation.gifs

import com.example.giphytask.presentation.Communication
import com.example.giphytask.presentation.gifs.adapter.GifUi

/**
 * Обердка над моделями списка гифок
 */
class GifsUi(private val list: List<GifUi>) {

    /**
     * Помещает данные в лайв дату
     */
    fun map(mapper: Communication.Mutate<List<GifUi>>) {
        mapper.map(list)
    }
}