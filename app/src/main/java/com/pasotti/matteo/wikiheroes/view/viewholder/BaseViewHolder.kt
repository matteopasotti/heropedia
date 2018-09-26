package com.pasotti.matteo.wikiheroes.view.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder(private val view : View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

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