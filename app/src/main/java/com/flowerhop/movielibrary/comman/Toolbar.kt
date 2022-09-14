package com.flowerhop.movielibrary.comman

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.databinding.ToolbarBinding

class Toolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private enum class LeftConfig(val imgResId: Int) {
        NONE(0), BACK(R.drawable.icon_back), CLOSE(R.drawable.icon_close)
    }

    private var binding: ToolbarBinding = ToolbarBinding.inflate(LayoutInflater.from(context), this)
    private val withSearch: Boolean
    private val leftConfig: LeftConfig
    private val titleGravity: Int
    private var title: String

    var onBackPressed: (() -> Unit)? = null
    var onClosePressed: (() -> Unit)? = null
    var onSearchClick: (() -> Unit)? = null

    init {
        if (attrs == null) {
            withSearch = false
            leftConfig = LeftConfig.BACK
            titleGravity = 0
            title = ""
        } else {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToolBar, defStyleAttr, 0)
            withSearch = typedArray.getBoolean(R.styleable.ToolBar_withSearch, false)
            leftConfig = when (typedArray.getInt(R.styleable.ToolBar_leftConfig, 0)) {
                1 -> LeftConfig.BACK
                2 -> LeftConfig.CLOSE
                else -> LeftConfig.NONE
            }

            titleGravity = typedArray.getInt(R.styleable.ToolBar_titleGravity, 0)
            title = typedArray.getString(R.styleable.ToolBar_title) ?: ""
            typedArray.recycle()
        }

        setBackgroundColor(ContextCompat.getColor(context, R.color.primary))

        setupLeftConfig()
        setupTitle()
        setupSearchIcon()
    }

    fun setTitle(title: String) {
        this.title = title
        binding.title.text = title
    }

    private fun setupLeftConfig() {
        when (leftConfig) {
            LeftConfig.NONE -> binding.iconLeft.isVisible = false
            else -> {
                binding.iconLeft.isVisible = true
                binding.iconLeft.setImageResource(leftConfig.imgResId)
                binding.iconLeft.setOnClickListener {
                    when(leftConfig) {
                        LeftConfig.BACK -> onBackPressed?.invoke()
                        LeftConfig.CLOSE -> onClosePressed?.invoke()
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setupTitle() {
        when(titleGravity) {
            1 -> binding.title.gravity = Gravity.CENTER
            2 -> binding.title.gravity = Gravity.END.or(Gravity.CENTER_VERTICAL)
            else -> {}
        }

        binding.title.text = title
    }

    private fun setupSearchIcon() {
        binding.iconSearch.isVisible = withSearch
        binding.iconSearch.setOnClickListener { onSearchClick?.invoke() }
    }
}