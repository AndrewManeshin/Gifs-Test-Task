package com.example.giphytask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.giphytask.core.App
import com.example.giphytask.core.Contract
import com.example.giphytask.presentation.gifs.GifsFragment
import com.example.giphytask.presentation.gifs.GifsLoadManager
import com.example.giphytask.presentation.gifs.GifsViewModel
import com.example.giphytask.presentation.details.DetailsFragment
import com.example.giphytask.presentation.details.GifDetailsViewModel

class MainActivity : AppCompatActivity(), Contract.App {

    private val gifsFragment = GifsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction() //TODO add save selected fragment when the screen is rotated
            .replace(R.id.container, gifsFragment)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override val gifsViewModel: GifsViewModel
        get() = (applicationContext as App).gifsViewModel

    override val gifsManager: GifsLoadManager
        get() = (applicationContext as App).gifsManager

    override val gifDetailsViewModel: GifDetailsViewModel
        get() = (applicationContext as App).gifDetailsViewModel

    override fun launchDetailsFragment(gifId: String) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction()
            .hide(gifsFragment)
            .add(R.id.container, DetailsFragment.newInstance(gifId))
            .addToBackStack(null)
            .commit()
    }
}