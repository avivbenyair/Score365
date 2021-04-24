package com.example.score365test.view

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.score365test.R
import com.example.score365test.base.GenericViewHolder
import com.example.score365test.model.UiGameDataCell

class GameVH(view: View) : GenericViewHolder<UiGameDataCell>(view) {
    private val mComp1FlagImg: AppCompatImageView = view.findViewById(R.id.comp_1_flag)
    private val mComp1NameTxt: TextView = view.findViewById(R.id.comp_1_name)
    private val mComp1ScoreTxt: TextView = view.findViewById(R.id.score_comp_1)
    private val mComp2FlagImg: AppCompatImageView = view.findViewById(R.id.comp_2_flag)
    private val mComp2NameTxt: TextView = view.findViewById(R.id.comp_2_name)
    private val mComp2ScoreTxt: TextView = view.findViewById(R.id.score_comp_2)
    private val mGameDateTxt: TextView = view.findViewById(R.id.game_date)
    private val mScoreGroup: Group = view.findViewById(R.id.score_grope)
    private val mGameStatus: TextView = view.findViewById(R.id.game_status)


    override fun setDataOnView(data: UiGameDataCell) {
        data.apply {
            mComp1NameTxt.text = gameComp1?.name
            mComp2NameTxt.text = gameComp2?.name

            Glide.with(itemView.context).load(gameComp1?.imageUrl).transition(
                DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.place_holder).into(mComp1FlagImg)

            Glide.with(itemView.context).load(gameComp2?.imageUrl).transition(
                DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.place_holder).into(mComp2FlagImg)

            isGameStarted?.let { isGameStarted ->
                if (isGameStarted) {
                    mComp1ScoreTxt.text = gameComp1?.score
                    mComp2ScoreTxt.text = gameComp2?.score

                    mGameStatus.text = if (isGameActive) itemView.context.getString(R.string.game_active_status) else
                        itemView.context.getString(R.string.game_ended_status)

                    mScoreGroup.visibility = View.VISIBLE
                    mGameStatus.visibility = View.VISIBLE
                    mGameDateTxt.visibility = View.INVISIBLE
                } else {
                    mGameDateTxt.text = gameTime
                    mGameDateTxt.visibility = View.VISIBLE
                    mGameStatus.visibility = View.INVISIBLE
                    mScoreGroup.visibility = View.INVISIBLE
                }
            }

        }

    }

}