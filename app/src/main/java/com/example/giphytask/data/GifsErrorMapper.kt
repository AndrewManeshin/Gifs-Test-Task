package com.example.giphytask.data

import com.example.giphytask.R
import com.example.giphytask.core.Mapper
import com.example.giphytask.core.ResourceManager
import java.net.UnknownHostException

/**
 * Класс мапинга ошибок при получении данных от сервера
 */
interface GifsErrorMapper : Mapper<String, Exception> {

    class Base(
        private val resourceManager: ResourceManager
    ) : GifsErrorMapper {
        override fun map(source: Exception) = when (source) {
            is UnknownHostException -> resourceManager.string(R.string.no_internet_connection)
            else -> resourceManager.string(R.string.service_unavailable)
        }
    }
}