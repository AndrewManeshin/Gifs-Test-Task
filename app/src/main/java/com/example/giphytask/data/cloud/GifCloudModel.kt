package com.example.giphytask.data.cloud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Модели данных приходящие от сервера
 */
@Serializable
class GifsCloudResult(
    @SerialName("data")
    val data: ArrayList<GifCloud>,
    @SerialName("pagination")
    val pagination: PaginationCloud
)

@Serializable
class GifCloud(
    @SerialName("id")
    val id: String,
    @SerialName("images")
    val images: ImageCloud,
    @SerialName("title")
    val title: String
)

@Serializable
class ImageCloud(
    @SerialName("downsized")
    val downsized: DownsizedImageCloud
)

@Serializable
class DownsizedImageCloud(
    @SerialName("url")
    val url: String = "https://media0.giphy.com/media/YRtLgsajXrz1FNJ6oy/giphy.gif?cid=f6e2b00agnly46mgd1mkfo8350xessthzhhqywxvqo4pobkd&rid=giphy.gif&ct=g"
)

interface Pagination {
    fun needToLoadMoreData(): Boolean
    fun nextPage(): Int
}

@Serializable
class PaginationCloud(
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("offset")
    val offset: Int
) : Pagination {

    override fun nextPage(): Int = +1
    override fun needToLoadMoreData(): Boolean = totalCount - offset != 0
}