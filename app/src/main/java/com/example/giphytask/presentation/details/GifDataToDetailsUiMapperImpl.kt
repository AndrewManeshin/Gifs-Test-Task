package com.example.giphytask.presentation.details

import com.example.giphytask.presentation.gifs.GifDataToDetailsUiMapper

/**
 * Реализация маппера для преобразования данных к юай слою
 */
class GifDataToDetailsUiMapperImpl : GifDataToDetailsUiMapper {
    override fun map(id: String, url: String, title: String) = DetailsUi(id, url, title)
}