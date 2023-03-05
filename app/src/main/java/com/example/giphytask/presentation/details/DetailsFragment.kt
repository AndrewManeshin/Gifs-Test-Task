package com.example.giphytask.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.giphytask.core.contract
import com.example.giphytask.databinding.FragmentGifsDetailsBinding

/**
 * Фрагмент для отображения информации по определенной гиф
 */
class DetailsFragment : Fragment() {

    private var _binding: FragmentGifsDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val GIF_ID_KEY = "GIF_ID_KEY"

        fun newInstance(gifId: String): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundleOf(GIF_ID_KEY to gifId)
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGifsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = this.contract().gifDetailsViewModel
        val gifDetailsId = arguments?.getString(GIF_ID_KEY)
            ?: throw IllegalStateException("use method newInstance")

        viewModel.fetchGifDetails(gifDetailsId)

        viewModel.observe(this) {
            it.show(binding.gifsUrlTextView, binding.gifsTitleTextView)
        }
    }
}