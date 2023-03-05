package com.example.giphytask.core

import android.app.Application
import com.example.giphytask.data.*
import com.example.giphytask.data.cloud.GifsCloudDataSource
import com.example.giphytask.data.cloud.GifsCloudToDataMapper
import com.example.giphytask.data.cloud.GifsService
import com.example.giphytask.presentation.details.DetailsCommunication
import com.example.giphytask.presentation.details.GifDataToDetailsUiMapperImpl
import com.example.giphytask.presentation.details.GifDetailsViewModel
import com.example.giphytask.presentation.gifs.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Кастомный класс для приложения
 */
class App : Application() {

    private companion object {
        const val BASE_URL = "https://api.giphy.com/v1/gifs/"
        const val API_KEY = "bNEPSYrOymOiCMJZDQR1X1R1nNgNjgY6"
    }

    val gifsManager = GifsLoadManager.Base(this)

    lateinit var gifsViewModel: GifsViewModel
    lateinit var gifDetailsViewModel: GifDetailsViewModel

    override fun onCreate() {
        super.onCreate()
        val client = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client).build()

        val gifsService = retrofit.create(GifsService::class.java)

        val gifsCloudDataSource =
            GifsCloudDataSource.Base(gifsService, API_KEY, GifsCloudToDataMapper())

        val resourceManager = ResourceManager.Base(this)
        val repository =
            GifsRepository.Base(
                GifsUiMapper(
                    GifDataToUIMapperImpl(),
                    resourceManager
                ),
                GifDataToDetailsUiMapperImpl(),
                GifsErrorMapper.Base(
                    resourceManager
                ),
                gifsCloudDataSource
            )

        gifsViewModel = GifsViewModel(
            repository,
            GifsCommunication.Base(),
            DispatchersList.Base()
        )

        gifDetailsViewModel = GifDetailsViewModel(repository, DetailsCommunication.Base())

        gifsManager.clear()
    }
}