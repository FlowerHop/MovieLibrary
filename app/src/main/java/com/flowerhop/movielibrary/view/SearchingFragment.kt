package com.flowerhop.movielibrary.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.comman.Navigation
import com.flowerhop.movielibrary.di.Providers
import com.flowerhop.movielibrary.domain.model.Movie
import com.flowerhop.movielibrary.presentation.MovieCategoryAdapter
import com.flowerhop.movielibrary.presentation.MoviesAdapter
import com.flowerhop.movielibrary.presentation.pagelist.MovieSearchingViewModel
import kotlinx.android.synthetic.main.fragment_searching.*

class SearchingFragment: Fragment(R.layout.fragment_searching) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            show()
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        searchEditText.requestFocus()
        searchEditTextLayout.setEndIconOnClickListener {
            searchEditText.text?.clear()
        }

        defaultCategory.text = MovieSearchingViewModel.DEFAULT_CATEGORY.name
        val adapter = MoviesAdapter()
        val movieSearchingViewModel = Providers.provideMovieSearchingViewModel(this)

        val defaultAdapter = MovieCategoryAdapter() {
            val movie = movieSearchingViewModel.defaultMovies.value?.get(it) ?: return@MovieCategoryAdapter
            navigateToMovieDetail(movie)
        }

        movieSearchingViewModel.movies.observe(viewLifecycleOwner) {
            val result = it.toMutableList()
            adapter.submitList(result)

            val noResult = result.isEmpty()
            list.visibility = if (noResult) View.GONE else View.VISIBLE
            defaultCategory.visibility = if (noResult) View.VISIBLE else View.GONE
            defaultList.visibility = if (noResult) View.VISIBLE else View.GONE
            val searchText = searchEditText.text.toString()

            invalidMessage.text = resources.getString(R.string.no_results_message, searchText)
            invalidMessage.visibility = if (searchText.isEmpty() || !noResult) View.GONE else View.VISIBLE
            divider.visibility = invalidMessage.visibility
        }
        movieSearchingViewModel.defaultMovies.observe(viewLifecycleOwner) {
            defaultAdapter.submitList(it.toMutableList())
        }

        list.apply {
            val gridLayoutManager = list.layoutManager as GridLayoutManager
            this.adapter = adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState != RecyclerView.SCROLL_STATE_IDLE) return
                    val currentItemPosition = gridLayoutManager.findLastVisibleItemPosition()
                    movieSearchingViewModel.loadMoreIfNeed(currentItemPosition)
                }
            })
        }

        defaultList.adapter = defaultAdapter

        searchEditText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    v?.let {
                        movieSearchingViewModel.search(it.text.trim().toString())
                        leaveInputSession()
                    }
                    return true
                }

                return false
            }
        })

        movieSearchingViewModel.search("")
    }

    private fun navigateToMovieDetail(movie: Movie) {
        leaveInputSession()
        Navigation.toMovieDetailByReplacing(requireActivity().supportFragmentManager, R.id.fragmentContainer, movie.id)
    }

    private fun leaveInputSession() {
        searchEditText.clearFocus()
        hideKeyBoard()
    }

    private fun hideKeyBoard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }

    companion object {
        const val TAG = "SearchingFragment"
    }
}