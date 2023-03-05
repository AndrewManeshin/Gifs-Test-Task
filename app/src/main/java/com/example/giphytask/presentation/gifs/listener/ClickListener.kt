package com.example.giphytask.presentation.gifs.listener

/**
 * Слушатель нажатий для списка гифок
 */
interface ClickListener {

    interface GifItem : ClickListener {
        fun showGifInfo(id: String)
    }

    interface TryAgainButton : ClickListener {
        fun tryLoadDataAgain()
    }

    interface GifsFragment : GifItem, TryAgainButton
}

