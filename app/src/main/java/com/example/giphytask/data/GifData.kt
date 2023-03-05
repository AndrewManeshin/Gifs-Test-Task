package com.example.giphytask.data

import com.example.giphytask.core.Mapper
import com.example.giphytask.presentation.gifs.GifDataToDetailsUiMapper
import com.example.giphytask.presentation.gifs.GifDataToUIMapper

/**
 * Данные дата слоя
 */
class GifData(
    private val id: String,
    private val url: String,
    private val title: String
) : Mapper<Boolean, String> {
    fun map(mapper: GifDataToUIMapper) = mapper.map(id, url)

    fun map(mapper: GifDataToDetailsUiMapper) = mapper.map(id, url, title)

    override fun map(source: String) = id == source
}