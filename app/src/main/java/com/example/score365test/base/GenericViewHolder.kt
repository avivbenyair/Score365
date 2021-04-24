package com.example.score365test.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class GenericViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun setDataOnView(data: T)
}