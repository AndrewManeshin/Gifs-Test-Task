package com.example.giphytask.presentation.gifs.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * Реализация DiffUtils
 */
class GifsDiffCallback(
    private val oldList: List<GifUi>, private val newList: List<GifUi>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].same(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].sameContent(newList[newItemPosition])
}