package com.example.score365test

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.score365test.base.BaseActivity
import com.example.score365test.model.UIResponse
import com.example.score365test.view.GamesAdapter


class MainScreenActivity : BaseActivity<MainScreenVM>() {

    private lateinit var mGamesList: RecyclerView
    private lateinit var mGameAdapter: GamesAdapter
    private lateinit var mProgressBar: LottieAnimationView

    override fun setVMClass() = MainScreenVM::class.java


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mGamesList = findViewById(R.id.list)
        mProgressBar = findViewById(R.id.progress_circular)
        initGameList()

        subscribeDataChanges()
    }


    private fun subscribeDataChanges() {
        mViewModel.games.observe(this, Observer<UIResponse> { it ->
            it?.let {
                mGameAdapter.setData(it.changedUiGamesIndexes ,it.newUiGamesCellsList)
                mProgressBar.visibility = View.GONE
            }
        })
            mViewModel.error.observe(this, Observer<String> {
                it?.let {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                    mProgressBar.visibility = View.GONE
                }
            })

    }
    private fun initGameList() {
            val lm = LinearLayoutManager(this)
            mGamesList.layoutManager = lm
            mGameAdapter = GamesAdapter()
            val dividerItemDecoration = DividerItemDecoration(mGamesList.context, lm.orientation)
            mGamesList.addItemDecoration(dividerItemDecoration)
            mGamesList.adapter = mGameAdapter
        }
    }