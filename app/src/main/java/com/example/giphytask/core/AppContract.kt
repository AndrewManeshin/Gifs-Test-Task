package com.example.giphytask.core

import com.example.giphytask.presentation.gifs.GifsFragment
import com.example.giphytask.presentation.gifs.GifsLoadManager
import com.example.giphytask.presentation.gifs.GifsViewModel
import com.example.giphytask.presentation.details.DetailsFragment
import com.example.giphytask.presentation.details.GifDetailsViewModel

/**
 * Contract - интерфейс для получения объектов в фрагменты, реализующих паттерн singleton
 */
fun GifsFragment.contract(): Contract.Gifs = requireActivity() as Contract.Gifs
fun DetailsFragment.contract(): Contract.Details = requireActivity() as Contract.Details

interface Contract {

    interface Gifs {
        val gifsViewModel: GifsViewModel
        val gifsManager: GifsLoadManager
        fun launchDetailsFragment(gifId: String)
    }

    interface Details {
        val gifDetailsViewModel: GifDetailsViewModel
    }

    interface App : Gifs, Details
}