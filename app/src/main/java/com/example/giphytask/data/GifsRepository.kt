package com.example.giphytask.data

import com.example.giphytask.core.Mapper
import com.example.giphytask.data.cloud.GifsCloudDataSource
import com.example.giphytask.presentation.gifs.GifsUi
import com.example.giphytask.presentation.details.DetailsUi
import com.example.giphytask.presentation.gifs.GifDataToDetailsUiMapper

/**
 * Репозиторий для DetailsFragment
 */
interface DetailsRepository {

    /**
     * @param id идентификатор необходимой гиф
     * @return Данные по гифке
     */
    fun fetchGifDetails(id: String): DetailsUi
}

/**
 * Репозиторий для GifsFragment
 */
interface GifsRepository {

    /**
     * @param searchWord строка для поиска, необязательныый параментр
     * @return Данные юай слоя
     */
    suspend fun fetchGifs(searchWord: String = ""): GifsUi

    /**
     * @param lastVisibleItemPosition позиция последнего видимого элемента
     */
    fun needToLoadMoreData(lastVisibleItemPosition: Int): Boolean

    class Base(
        private val gifsUiMapper: Mapper.Ui<GifsUi, List<GifData>>,
        private val gifDetailsMapper: GifDataToDetailsUiMapper,
        private val errorMapper: Mapper<String, Exception>,
        private val cloudDataSource: GifsCloudDataSource
    ) : GifsRepository, DetailsRepository {

        private val cachedList = ArrayList<GifData>()
        private var lastSearchWord: String = ""

        override suspend fun fetchGifs(searchWord: String): GifsUi = try {
            if (lastSearchWord != searchWord) {
                cloudDataSource.clearPagination()
                cachedList.clear()
                lastSearchWord = searchWord
            }
            val result = cloudDataSource.fetchGifs(searchWord)
            cachedList.addAll(result)
            gifsUiMapper.map(cachedList)
        } catch (e: Exception) {
            gifsUiMapper.mapError(cachedList, errorMapper.map(e))
        }

        override fun needToLoadMoreData(lastVisibleItemPosition: Int) = with(cachedList) {
            cloudDataSource.needToLoadMoreData() && isNotEmpty() && size - 1 <= lastVisibleItemPosition
        }

        override fun fetchGifDetails(id: String) =
            cachedList.find { it.map(id) }!!.map(gifDetailsMapper)
    }
}