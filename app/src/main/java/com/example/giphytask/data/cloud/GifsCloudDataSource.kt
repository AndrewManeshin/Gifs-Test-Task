package com.example.giphytask.data.cloud

import com.example.giphytask.core.Mapper
import com.example.giphytask.data.GifData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * класс для получения данных из сети
 */
interface GifsCloudDataSource {

    suspend fun fetchGifs(searchWord: String): List<GifData>
    fun needToLoadMoreData(): Boolean
    fun clearPagination()

    companion object {
        const val limit = 25
    }

    class Base(
        private val service: GifsService, private val apiKey: String,
        private val mapper: Mapper<List<GifData>, GifsCloudResult>
    ) : GifsCloudDataSource {

        private val json = Json {
            ignoreUnknownKeys = true
        }
        private var pagination = PaginationCloud(0, -25)

        override suspend fun fetchGifs(searchWord: String): List<GifData> {
            val result = json.decodeFromString<GifsCloudResult>(
                if (searchWord.isEmpty()) {
                    service.fetchTrendingGifs(apiKey, limit, pagination.offset + limit).string()
                } else {
                    service.fetchSearchedGifs(apiKey, limit, pagination.offset + limit, searchWord)
                        .string()
                }
            )
            pagination = result.pagination
            return mapper.map(result)
        }

        override fun needToLoadMoreData() = pagination.needToLoadMoreData()

        override fun clearPagination() {
            pagination = PaginationCloud(0, -25)
        }
    }
}