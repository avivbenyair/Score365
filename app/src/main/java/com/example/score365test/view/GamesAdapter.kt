package com.example.score365test.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.score365test.R
import com.example.score365test.base.GenericViewHolder
import com.example.score365test.model.COMPETITION_TYPE
import com.example.score365test.model.UiGameDataCell


class GamesAdapter : RecyclerView.Adapter<GenericViewHolder<UiGameDataCell>>() {

    private var mData = listOf<UiGameDataCell>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericViewHolder<UiGameDataCell> {
        return when (viewType) {
            COMPETITION_TYPE -> CompetitionVH(
                LayoutInflater.from(parent.context).inflate(R.layout.cell_competition, parent, false))

            else -> GameVH(
                LayoutInflater.from(parent.context).inflate(R.layout.cell_game, parent, false)
            )
        }
    }

    override fun getItemCount() = mData.size

    override fun getItemViewType(position: Int) = mData[position].cellType

    override fun onBindViewHolder(holder: GenericViewHolder<UiGameDataCell>, position: Int) {
        holder.setDataOnView(mData[position])
    }

    fun setData(changedIndexes: List<Int>?, newData: List<UiGameDataCell>) {
        this.mData = newData
        if (changedIndexes.isNullOrEmpty()) {
            notifyDataSetChanged()
        } else {
            changedIndexes.forEach {
                notifyItemChanged(it)
            }
        }
    }
}