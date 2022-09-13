package com.flowerhop.movielibrary.comman

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class ToggleImageView: AppCompatImageView {
    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
    private val selector: StateListDrawable?
        get() = drawable as? StateListDrawable

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleRes: Int): super(context, attrs, defStyleRes)

    init {
        setOnClickListener { toggle() }
    }

    private var isChecked: Boolean = false

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val state = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(state, CHECKED_STATE_SET)
        }

        return state
    }

    fun toggle() {
        setChecked(!isChecked)
    }

    fun setChecked(isChecked: Boolean) {
        selector?.let {
            this.isChecked = isChecked
            refreshDrawableState()
        }
    }
}