package com.flowerhop.movielibrary.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.di.Providers
import com.flowerhop.movielibrary.presentation.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_searching.*

class SearchingFragment: Fragment(R.layout.fragment_searching) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchEditText.requestFocus()
        searchEditTextLayout.setEndIconOnClickListener {
            searchEditText.text?.clear()
        }

        val adapter = MoviesAdapter()
        val movieSearchingViewModel = Providers.provideMovieSearchingViewModel(this).apply {
            movies.observe(viewLifecycleOwner) {
                adapter.submitList(it.toMutableList())
            }
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

        searchEditText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    v?.let {
                        movieSearchingViewModel.search(it.text.trim().toString())

                        val inputMethodManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
                        it.clearFocus()
                    }
                    return true
                }

                return false
            }
        })
    }

    companion object {
        const val TAG = "SearchingFragment"
    }
}