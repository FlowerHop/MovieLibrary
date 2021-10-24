package com.flowerhop.movielibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowerhop.movielibrary.databinding.SimpleCategoryListBinding
import kotlinx.android.synthetic.main.simple_category_list.view.*

class SimpleCategoryList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
    interface OnEventListener {
        fun onViewAll()
    }

    private val binding: SimpleCategoryListBinding
    var onEventListener: OnEventListener? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = SimpleCategoryListBinding.inflate(inflater, this).apply {
            more.setOnClickListener { onEventListener?.onViewAll() }
        }
    }
}