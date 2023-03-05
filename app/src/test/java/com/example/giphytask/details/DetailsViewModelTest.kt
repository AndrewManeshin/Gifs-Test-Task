package com.example.giphytask.details

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.giphytask.data.DetailsRepository
import com.example.giphytask.presentation.details.DetailsCommunication
import com.example.giphytask.presentation.details.DetailsUi
import com.example.giphytask.presentation.details.GifDetailsViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class DetailsViewModelTest {

    @Test
    fun `test load gif details`() {
        val repository = FakeDetailsRepository()
        val communication = FakeDetailsCommunication()

        val viewModel = GifDetailsViewModel(
            repository,
            communication
        )

        repository.expectedResult = DetailsUi("test", "test", "test")
        viewModel.fetchGifDetails("test")

        assertEquals("test", repository.receivedId)
        assertEquals(
            DetailsUi("test", "test", "test"),
            communication.receivedDetailsUi
        )
    }

    private class FakeDetailsRepository : DetailsRepository {

        var receivedId = ""
        var expectedResult: DetailsUi = DetailsUi("", "", "")

        override fun fetchGifDetails(id: String): DetailsUi {
            receivedId = id
            return expectedResult
        }
    }

    private class FakeDetailsCommunication : DetailsCommunication {

        var receivedDetailsUi = DetailsUi("", "", "")

        override fun map(source: DetailsUi) {
            receivedDetailsUi = source
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<DetailsUi>) = Unit
    }
}