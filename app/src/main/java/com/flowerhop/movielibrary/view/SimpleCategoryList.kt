package com.flowerhop.movielibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowerhop.movielibrary.databinding.SimpleCategoryListBinding

class SimpleCategoryList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    interface OnEventListener {
        fun onViewAll()
        fun onScrolledPosition(position: Int)
    }

    private val binding: SimpleCategoryListBinding
    private var onEventListener: OnEventListener? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = SimpleCategoryListBinding.inflate(inflater, this).apply {
            nowPlayingMore.setOnClickListener { onEventListener?.onViewAll() }
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            nowPlayingList.layoutManager = linearLayoutManager
            nowPlayingList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState != RecyclerView.SCROLL_STATE_IDLE) return
                    val currentItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                    onEventListener?.onScrolledPosition(currentItemPosition)
                }
            })
        }
    }

    fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        binding.nowPlayingList.adapter = adapter
        binding.nowPlayingList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}