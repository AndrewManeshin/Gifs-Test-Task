package com.example.giphytask.data.cloud

import com.example.giphytask.core.Mapper
import com.example.giphytask.data.GifData

/**
 * Маппер для преобразования модели получаемой от сервера к слою данных
 */
class GifsCloudToDataMapper : Mapper<List<GifData>, GifsCloudResult> {
    override fun map(source: GifsCloudResult): List<GifData> {
        return source.data.map {
            GifData(
                it.id,
                it.images.downsized.url,
                it.title
            )
        }
    }
}