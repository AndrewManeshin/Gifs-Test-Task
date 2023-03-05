package com.example.giphytask.data

import com.example.giphytask.R
import com.example.giphytask.core.Mapper
import com.example.giphytask.core.ResourceManager
import com.example.giphytask.presentation.gifs.GifDataToUIMapper
import com.example.giphytask.presentation.gifs.adapter.GifUi
import com.example.giphytask.presentation.gifs.GifsUi

/**
 * Маппинг данных дата слоя к данным юай слоя
 */
class GifsUiMapper(
    private val gifUiMapper: GifDataToUIMapper,
    private val resourceManager: ResourceManager
) : Mapper.Ui<GifsUi, List<GifData>> {

    override fun map(source: List<GifData>): GifsUi = when {
        source.isEmpty() -> GifsUi(listOf(GifUi.Empty(resourceManager.string(R.string.search_fail))))
        else -> GifsUi(ArrayList<GifUi>().apply {
            this.addAll(source.map { it.map(gifUiMapper) })
            this.add(GifUi.BottomLoader)
        })
    }

    override fun mapError(source: List<GifData>, message: String): GifsUi = when {
        source.isEmpty() -> GifsUi(listOf(GifUi.FullSizeError(message)))
        else -> GifsUi(ArrayList<GifUi>().apply {
            this.addAll(source.map { it.map(gifUiMapper) })
            this.add(GifUi.BottomError(message))
        })
    }
}