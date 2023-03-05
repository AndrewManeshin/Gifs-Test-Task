package com.example.giphytask.presentation.gifs

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Обердка над Glide для загрузки изображений
 */
interface GifsLoadManager {

    /**
     * Загрузка изображение
     * @param url ссылка на изображние
     * @param imageView вью куда необходимо поместить изображение
     */
    fun load(url: String, imageView: ImageView)

    interface Clear {

        /**
         * Метод для очистки кэша
         */
        fun clear()
    }

    class Base(
        private val context: Context
    ) : GifsLoadManager, Clear {

        override fun load(url: String, imageView: ImageView) {

            Glide.with(context)
                .asGif()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }

        override fun clear() {
            Thread() {
                Glide.get(context).clearDiskCache()
            }.start()
        }
    }
}


