package com.example.giphytask.data

import com.example.giphytask.presentation.gifs.GifDataToUIMapper
import com.example.giphytask.presentation.gifs.adapter.GifUi

/**
 * Реализация маппинга для маппинга даных дата слоя к юай слою
 */
class GifDataToUIMapperImpl : GifDataToUIMapper {
    override fun map(id: String, url: String) = GifUi.Basic(id, url)
}