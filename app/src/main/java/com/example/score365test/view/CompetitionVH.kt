package com.example.score365test.view

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.score365test.R
import com.example.score365test.base.GenericViewHolder
import com.example.score365test.model.UiGameDataCell

class CompetitionVH(view: View) : GenericViewHolder<UiGameDataCell>(view) {
    private val mNameTxt: TextView = view.findViewById(R.id.comp_name)
    private val mFlagImg: AppCompatImageView = view.findViewById(R.id.comp_flag)

    override fun setDataOnView(data: UiGameDataCell) {
        Glide.with(mFlagImg).load(data.competitionFlagUrl).placeholder(R.drawable.place_holder)
            .into(mFlagImg)
        mNameTxt.text = data.competitionName
    }
}
