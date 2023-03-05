package com.example.giphytask.presentation.gifs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giphytask.core.contract
import com.example.giphytask.databinding.FragmentGifsListBinding
import com.example.giphytask.presentation.gifs.adapter.GifsAdapter
import com.example.giphytask.presentation.gifs.listener.ClickListener
import com.example.giphytask.presentation.gifs.listener.SearchViewListener

/**
 * Фрагмент для отображения списка гифок
 */
class GifsFragment : Fragment(), ClickListener.GifsFragment, SearchViewListener {

    private var _binding: FragmentGifsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        contract().gifsViewModel
    }
    private val linearLayoutManager = LinearLayoutManager(context)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGifsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GifsAdapter(this, contract().gifsManager)

        with(binding.recyclerView) {
            itemAnimator = null;
            layoutManager = linearLayoutManager
            this.adapter = adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    viewModel.fetchMoreGifs(linearLayoutManager.findLastVisibleItemPosition())
                }

            })
        }

        viewModel.observeGifs(this) {
            adapter.update(it)
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun showGifInfo(id: String) {
        contract().launchDetailsFragment(id)
    }

    override fun tryLoadDataAgain() {
        viewModel.fetchGifs()
        linearLayoutManager.scrollToPosition(linearLayoutManager.findLastVisibleItemPosition())
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.fetchGifs(query ?: "")
        val imm = getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.equals("")) {
            this.onQueryTextSubmit("");
        }
        return true;
    }

    override fun onResume() {
        binding.searchView.setOnQueryTextListener(this)
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}