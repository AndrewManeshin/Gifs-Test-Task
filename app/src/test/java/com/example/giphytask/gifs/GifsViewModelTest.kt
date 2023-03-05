package com.example.giphytask.gifs

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.giphytask.core.DispatchersList
import com.example.giphytask.data.GifsRepository
import com.example.giphytask.presentation.gifs.GifsCommunication
import com.example.giphytask.presentation.gifs.GifsUi
import com.example.giphytask.presentation.gifs.GifsViewModel
import com.example.giphytask.presentation.gifs.adapter.GifUi
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GifsViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `test init and re-init`() = runBlocking {
        val repository = FakeGifsRepository()
        val communication = FakeGifsCommunication()

        val viewModel = GifsViewModel(
            repository,
            communication,
            TestDispatchersList()
        )

        repository.changeExpectedResult(
            GifsUi(
                listOf(
                    GifUi.Basic("1", "1"),
                    GifUi.Basic("2", "2")
                )
            )
        )

        viewModel.init(true)

        assertEquals(true, communication.mapGifsCalledList[0][0] is GifUi.FullSizeLoader)
        assertEquals(
            listOf(
                GifUi.Basic("1", "1"),
                GifUi.Basic("2", "2")
            ),
            communication.mapGifsCalledList[1]
        )

        viewModel.init(false)

        assertEquals(true, communication.mapGifsCalledList[0][0] is GifUi.FullSizeLoader)
        assertEquals(
            listOf(
                GifUi.Basic("1", "1"),
                GifUi.Basic("2", "2")
            ),
            communication.mapGifsCalledList[1]
        )
        assertEquals(2, communication.mapGifsCalledList.size)
        assertEquals(1, repository.fetchGifsCalledCount)
    }

    @Test
    fun `test load gifs by searched word`() = runBlocking {
        val repository = FakeGifsRepository()
        val communication = FakeGifsCommunication()

        val viewModel = GifsViewModel(
            repository,
            communication,
            TestDispatchersList()
        )

        repository.changeExpectedResult(
            GifsUi(
                listOf(
                    GifUi.Basic("1", "1"),
                    GifUi.Basic("2", "2")
                )
            )
        )

        viewModel.init(true)

        assertEquals(true, communication.mapGifsCalledList[0][0] is GifUi.FullSizeLoader)
        assertEquals(
            listOf(
                GifUi.Basic("1", "1"),
                GifUi.Basic("2", "2")
            ),
            communication.mapGifsCalledList[1]
        )

        val expected = "test"
        viewModel.fetchGifs("test")
        assertEquals(expected, repository.recievedSearchWord)
    }

    @Test
    fun `test load more data`() = runBlocking {
        val repository = FakeGifsRepository()
        val communication = FakeGifsCommunication()

        val viewModel = GifsViewModel(
            repository,
            communication,
            TestDispatchersList()
        )

        repository.changeExpectedResult(
            GifsUi(
                listOf(
                    GifUi.Basic("1", "1"),
                    GifUi.Basic("2", "2")
                )
            )
        )

        viewModel.init(true)

        assertEquals(true, communication.mapGifsCalledList[0][0] is GifUi.FullSizeLoader)
        assertEquals(
            listOf(
                GifUi.Basic("1", "1"),
                GifUi.Basic("2", "2")
            ),
            communication.mapGifsCalledList[1]
        )

        repository.changeExpectedResult(
            GifsUi(
                listOf(
                    GifUi.Basic("3", "3"),
                    GifUi.Basic("4", "4")
                )
            )
        )

        repository.changeNeedToLoadResult(true)
        viewModel.fetchMoreGifs(1)

        assertEquals(2, repository.fetchGifsCalledCount)
        assertEquals(3, communication.mapGifsCalledList.size)
        assertEquals(
            listOf(
                GifUi.Basic("3", "3"),
                GifUi.Basic("4", "4")
            ),
            communication.mapGifsCalledList[2]
        )
    }

    private class FakeGifsRepository : GifsRepository {

        var fetchGifsCalledCount = 0
        var recievedSearchWord = ""
        private var expected: GifsUi = GifsUi(listOf())
        private var needToLOad: Boolean = false

        fun changeExpectedResult(expected: GifsUi) {
            this.expected = expected
        }

        fun changeNeedToLoadResult(newValue: Boolean) {
            needToLOad = newValue
        }

        override suspend fun fetchGifs(searchWord: String): GifsUi {
            recievedSearchWord = searchWord
            fetchGifsCalledCount++
            return expected
        }

        override fun needToLoadMoreData(lastVisibleItemPosition: Int): Boolean {
            return needToLOad
        }
    }

    private class FakeGifsCommunication : GifsCommunication {

        val mapGifsCalledList = mutableListOf<List<GifUi>>()

        override fun map(source: List<GifUi>) {
            mapGifsCalledList.add(source)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<GifUi>>) = Unit
    }

    private class TestDispatchersList : DispatchersList {
        override fun io() = TestCoroutineDispatcher()
        override fun ui() = TestCoroutineDispatcher()
    }
}