package com.pasotti.matteo.wikiheroes.view.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder(private val view : View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

    init {
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
    }

    @Throws(Exception::class)
    abstract fun bindData(data: Any?)

    protected fun view(): View {
        return view
    }

    protected fun context(): Context {
        return view.context
    }
}