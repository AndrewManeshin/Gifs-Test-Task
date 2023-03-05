package com.example.giphytask.presentation.details

import android.widget.TextView

/**
 * Модель данных для экрана с отображением деталей гиф
 */
data class DetailsUi(
    private val id: String,
    private val url: String,
    private val title: String
) {
    fun map(mapper: DetailsCommunication) = mapper.map(this)

    fun show(urlTextView: TextView, titleTextView: TextView) {
        urlTextView.text = url
        titleTextView.text = title
    }
}