package com.example.giphytask.presentation.gifs

import com.example.giphytask.presentation.details.DetailsUi
import com.example.giphytask.presentation.gifs.adapter.GifUi

/**
 * Интерфейсы для преобразования данных дата слоя к юай слою
 */
interface GifDataToUIMapper {
    fun map(id: String, url: String): GifUi
}

interface GifDataToDetailsUiMapper {
    fun map(id: String, url: String, title: String): DetailsUi
}