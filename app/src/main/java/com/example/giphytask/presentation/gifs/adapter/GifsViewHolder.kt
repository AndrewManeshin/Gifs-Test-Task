package com.example.giphytask.presentation.gifs.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.giphytask.databinding.*
import com.example.giphytask.presentation.gifs.GifsLoadManager
import com.example.giphytask.presentation.gifs.listener.ClickListener

/**
 * Вьюхолдеры для разных видов на экране изображений
 */
abstract class GifsViewHolder(view: View) : RecyclerView.ViewHolder(view), ToUiMapper.Gif {

    fun bind(item: GifUi) = item.map(this)

    class Basic(
        private val binding: GifsLayoutBinding,
        private val gifsManager: GifsLoadManager,
        private val clickListener: ClickListener.GifItem
    ) :
        GifsViewHolder(binding.root) {
        override fun mapBase(id: String, url: String) {
            gifsManager.load(url, binding.gifImageView)
            binding.root.setOnClickListener {
                clickListener.showGifInfo(id)
            }
        }
    }

    abstract class Message(view: View, private val textView: TextView) : GifsViewHolder(view) {
        override fun mapMessage(message: String) {
            textView.text = message
        }
    }

    class Empty(binding: EmptyLayoutBinding) : Message(binding.root, binding.messageTextView)

    abstract class Error(
        view: View,
        textView: TextView,
        private val button: Button,
        private val listener: ClickListener.TryAgainButton
    ) : Message(view, textView) {
        override fun mapMessage(message: String) {
            super.mapMessage(message)
            button.setOnClickListener {
                listener.tryLoadDataAgain()
            }
        }
    }

    class FullSizeError(
        binding: FullsizeErrorLayoutBinding, tryAgainListener: ClickListener.TryAgainButton
    ) : Error(
        binding.root,
        binding.fullSizeErrorTextView,
        binding.fullSizeErrorTryAgainButton,
        tryAgainListener
    )

    class BottomError(
        binding: BottomErrorLayoutBinding, tryAgainListener: ClickListener.TryAgainButton
    ) : Error(
        binding.root,
        binding.bottomErrorTextView,
        binding.bottomErrorTryAgainButton,
        tryAgainListener
    )

    class BottomLoader(binding: BottomLoaderLayoutBinding) : GifsViewHolder(binding.root)
    class FullSizeLoader(binding: FullsizeLoaderLayoutBinding) : GifsViewHolder(binding.root)
}