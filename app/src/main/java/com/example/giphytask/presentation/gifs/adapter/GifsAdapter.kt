package com.example.giphytask.presentation.gifs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.giphytask.databinding.*
import com.example.giphytask.presentation.gifs.GifsLoadManager
import com.example.giphytask.presentation.gifs.listener.ClickListener

/**
 * Адаптер для списка гифок
 */
class GifsAdapter(
    private val clickListener: ClickListener.GifsFragment,
    private val gifsManager: GifsLoadManager
) : RecyclerView.Adapter<GifsViewHolder>() {

    private val items = ArrayList<GifUi>()

    override fun getItemViewType(position: Int) = items[position].type().ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        GifUiType.EMPTY.ordinal -> GifsViewHolder.Empty(
            EmptyLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        GifUiType.BASIC.ordinal -> GifsViewHolder.Basic(
            GifsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            gifsManager,
            clickListener
        )
        GifUiType.FULL_SIZE_LOADER.ordinal -> GifsViewHolder.FullSizeLoader(
            FullsizeLoaderLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        GifUiType.FULL_SIZE_ERROR.ordinal -> GifsViewHolder.FullSizeError(
            FullsizeErrorLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), clickListener
        )
        GifUiType.BOTTOM_LOADER.ordinal -> GifsViewHolder.BottomLoader(
            BottomLoaderLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        GifUiType.BOTTOM_ERROR.ordinal -> GifsViewHolder.BottomError(
            BottomErrorLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), clickListener
        )
        else -> throw java.lang.IllegalStateException("unknown viewType $viewType")
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    fun update(newList: List<GifUi>) {
        DiffUtil.calculateDiff(GifsDiffCallback(items, newList)).dispatchUpdatesTo(this)
        items.clear()
        items.addAll(newList)
    }
}


interface ToUiMapper {

    /**
     * Маппер для базового элемента в списке
     */
    interface Basic {
        fun mapBase(
            id: String,
            url: String,
        ): Unit = throw java.lang.IllegalStateException("cannot be used it type is not Base")
    }

    /**
     * Маппер для элемента содержащего только текст (Empty, Error)
     */
    interface Message {
        fun mapMessage(message: String): Unit =
            throw java.lang.IllegalStateException("cannot be used it type is not Empty and Error")
    }

    /**
     * Маппер для отображения данных в списке
     */
    interface Gif : Basic, Message
}



