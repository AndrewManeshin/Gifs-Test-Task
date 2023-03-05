package com.example.giphytask.presentation.gifs.adapter

import com.example.giphytask.core.Comparing

/**
 * Модель изображения для юай слоя
 */
interface GifUi : Comparing<GifUi> {

    fun map(mapper: ToUiMapper.Gif)
    fun type(): GifUiType

    /**
     * Модель без данных для отображения
     */
    abstract class AbstractNoData(private val type: GifUiType) : GifUi {
        override fun map(mapper: ToUiMapper.Gif) = Unit
        override fun type() = type
        override fun same(data: GifUi) = data.type() == this.type()
        override fun sameContent(data: GifUi) = same(data)
    }

    /**
     * Модель для отображением текста
     */
    abstract class Abstract(private val data: String, type: GifUiType) : AbstractNoData(type) {

        override fun map(mapper: ToUiMapper.Gif) = mapper.mapMessage(data)

        override fun same(data: GifUi) = if (data.type() == this.type()) {
            (data as Abstract).data == this.data
        } else
            false

        override fun sameContent(data: GifUi) = same(data)
    }

    /**
     * Базовый элемент списка
     */
    data class Basic(
        private val id: String,
        private val url: String,
    ) : Abstract(url, GifUiType.BASIC) {
        override fun map(mapper: ToUiMapper.Gif) = mapper.mapBase(id, url)
    }

    /**
     * Сообщает что список пуст и ничего не удалось найти
     */
    class Empty(message: String) : Abstract(message, GifUiType.EMPTY)

    /**
     * Полноэкранная ошибка
     */
    class FullSizeError(message: String) : Abstract(message, GifUiType.FULL_SIZE_ERROR)

    /**
     * Ошибка на дне списка
     */
    class BottomError(message: String) : Abstract(message, GifUiType.BOTTOM_ERROR)

    /**
     * Полноэкранный загрузчик
     */
    object FullSizeLoader : AbstractNoData(GifUiType.FULL_SIZE_LOADER)

    /**
     * Загрузчик на дне списка
     */
    object BottomLoader : AbstractNoData(GifUiType.BOTTOM_LOADER)
}

/**
 * Виды элементов списка изображений
 */
enum class GifUiType {
    BASIC,
    EMPTY,
    FULL_SIZE_LOADER,
    BOTTOM_LOADER,
    FULL_SIZE_ERROR,
    BOTTOM_ERROR,
}