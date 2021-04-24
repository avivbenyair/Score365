package com.example.score365test

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.score365test.base.BaseVM
import com.example.score365test.extentions.formatDate
import com.example.score365test.model.*
import com.example.score365test.network.Repository
import com.example.score365test.network.response.GameX
import com.example.score365test.network.response.GamesDataResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val GAME_COMPETITOR_FLAG_PREFIX =
    "https://imagescache.365scores.com/image/upload/w_48,h_48,c_limit,f_webp,q_90,d_Competitors:default1.png/Competitors/"
const val COMPETITION_FLAG_PREFIX_URL =
    "https://imagescache.365scores.com/image/upload/w_140,h_140,c_limit,f_webp,q_90,d_Countries:Round:default.png/Countries/Round/"
const val COMPETITOR_1_INDEX = 0
const val COMPETITOR_2_INDEX = 1
const val SERVER_DATE_FORMAT = "dd-MM-yyyy HH:mm"
const val CLIENT_DATE_FORMAT = "dd/MM/yy"
const val RELOAD_TIME_INTERVAL = 5000L

class MainScreenVM : BaseVM() {

    private val _games = MutableLiveData<UIResponse>()
    val games: LiveData<UIResponse> = _games
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private var lastUpdateID = ""

    override fun loadData() {
        Repository.retrieveGamesData(object : Callback<GamesDataResponse> {
            override fun onResponse(
                call: Call<GamesDataResponse>?,
                response: Response<GamesDataResponse>
            ) {
                CoroutineScope(Dispatchers.Default).launch {
                    val generatedUIResponse = generateNewUiDataCells(response)
                    postGeneratedGames(generatedUIResponse)
                    runReLoadLoop()
                }
            }

            override fun onFailure(call: Call<GamesDataResponse>?, t: Throwable?) {
                _error.value = "Something went wrong, please try again"
            }

        })


    }

    private fun runReLoadLoop() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(RELOAD_TIME_INTERVAL)
                reloadData()
            }
        }
    }

    private suspend fun postGeneratedGames(generatedUIResponse: UIResponse?) {
        withContext(Main) {
            _games.value = generatedUIResponse
        }
    }

    private suspend fun reloadData() {
        Repository.reRetrieveGamesData(lastUpdateID, object : Callback<GamesDataResponse> {
            override fun onResponse(
                call: Call<GamesDataResponse>?,
                response: Response<GamesDataResponse>
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    val generatedCells = renewUiDateCells(response)
                    postGeneratedGames(generatedCells)
                }
            }

            override fun onFailure(call: Call<GamesDataResponse>?, t: Throwable?) {
                _error.value = "reload failed, please wait for for new data"
            }

        })

    }

    @WorkerThread
    private fun generateNewUiDataCells(response: Response<GamesDataResponse>): UIResponse? {
        val newUiGameDataCellsList = mutableListOf<UiGameDataCell>()
        val checkedGamesList = mutableListOf<GameX>()
        lastUpdateID = response.body().LastUpdateID
        response.body().Competitions.forEach { updatedCompetition ->
            newUiGameDataCellsList.add(
                UiGameDataCell(
                    cellType = COMPETITION_TYPE,
                    id = updatedCompetition.ID,
                    competitionName = updatedCompetition.Name,
                    competitionFlagUrl = COMPETITION_FLAG_PREFIX_URL + updatedCompetition.CID
                )
            )


            response.body().Games.forEach { updatedGame ->
                if (updatedGame.Comp == updatedCompetition.ID) {
                    checkedGamesList.add(updatedGame)

                    var comp1score = ""
                    var comp2score = ""
                    val isGameStarted =
                        updatedGame.Scrs.size > 2 && updatedGame.Scrs[COMPETITOR_1_INDEX] > -1 && updatedGame.Scrs[COMPETITOR_2_INDEX] > -1
                    if (isGameStarted) {
                        comp1score = updatedGame.Scrs[COMPETITOR_1_INDEX].toInt().toString()
                        comp2score = updatedGame.Scrs[COMPETITOR_2_INDEX].toInt().toString()
                    }
                    newUiGameDataCellsList.add(
                        UiGameDataCell(
                            id = updatedGame.ID,
                            cellType = GAME_TYPE,
                            isGameStarted = isGameStarted,
                            isGameActive = updatedGame.Active,
                            gameTime = updatedGame.STime.formatDate(
                                SERVER_DATE_FORMAT,
                                CLIENT_DATE_FORMAT
                            ),
                            gameComp1 = UiCompetitor(
                                score = comp1score,
                                imageUrl = GAME_COMPETITOR_FLAG_PREFIX + updatedGame.Comps[COMPETITOR_1_INDEX].ID,
                                name = updatedGame.Comps[COMPETITOR_1_INDEX].Name
                            ),
                            gameComp2 = UiCompetitor(
                                score = comp2score,
                                imageUrl = GAME_COMPETITOR_FLAG_PREFIX + updatedGame.Comps[COMPETITOR_2_INDEX].ID,
                                name = updatedGame.Comps[COMPETITOR_2_INDEX].Name
                            )
                        )
                    )

                }


            }

            response.body().Games.removeAll(checkedGamesList)
        }

        return UIResponse(null, newUiGameDataCellsList)

    }

    @WorkerThread
    private fun renewUiDateCells(response: Response<GamesDataResponse>): UIResponse {
        val newUiGameDataCellsList = _games.value?.newUiGamesCellsList?.toMutableList()!!
        val outDatedGamesList = _games.value?.newUiGamesCellsList?.toMutableList()!!
        val changedGamesIndexes: MutableList<Int> = mutableListOf()
        val checkedGamesList: MutableList<UiGameDataCell> = mutableListOf()

        response.body().Games.forEach { updatedGame ->
            outDatedGamesList.forEachIndexed { index, outDatedGame ->
                if (outDatedGame.cellType == GAME_TYPE && outDatedGame.id == updatedGame.ID) {
                    changedGamesIndexes.add(index)

                    var comp1score = ""
                    var comp2score = ""

                    newUiGameDataCellsList[index]?.apply {
                        var isGameStarted = updatedGame.Scrs != null && (updatedGame.Scrs.size > 2 && updatedGame.Scrs[COMPETITOR_1_INDEX] > -1 && updatedGame.Scrs[COMPETITOR_2_INDEX] > -1)

                        if (isGameStarted &&  updatedGame.Scrs != null) {
                            comp1score = updatedGame.Scrs[COMPETITOR_1_INDEX].toInt().toString()
                            comp2score = updatedGame.Scrs[COMPETITOR_2_INDEX].toInt().toString()
                            gameComp1?.score = comp1score
                            gameComp2?.score = comp2score
                        }
                        if (updatedGame.Scrs != null){
                            this.isGameStarted = isGameStarted
                        }
                        isGameActive = updatedGame.Active
                    }
/*
                    newUiGameDataCellsList[index] = UiGameDataCell(
                        id = updatedGame.ID,
                        cellType = GAME_TYPE,
                        isGameStarted = isGameStarted,
                        isGameActive = updatedGame.Active,
                        gameTime = updatedGame.STime.formatDate(
                            SERVER_DATE_FORMAT,
                            CLIENT_DATE_FORMAT
                        ),
                        gameComp1 = UiCompetitor(
                            score = comp1score,
                            imageUrl = GAME_COMPETITOR_FLAG_PREFIX + updatedGame.Comps[COMPETITOR_1_INDEX].ID,
                            name = updatedGame.Comps[COMPETITOR_1_INDEX].Name
                        ),
                        gameComp2 = UiCompetitor(
                            score = comp2score,
                            imageUrl = GAME_COMPETITOR_FLAG_PREFIX + updatedGame.Comps[COMPETITOR_2_INDEX].ID,
                            name = updatedGame.Comps[COMPETITOR_2_INDEX].Name
                        )
                    )*/


                    /*   var comp1score = ""
                       var comp2score = ""
                       var isGameStarted =
                           updatedGame.Scrs.size > 2 && updatedGame.Scrs[COMPETITOR_1_INDEX] > -1 && updatedGame.Scrs[COMPETITOR_2_INDEX] > -1
                       if (isGameStarted) {
                           comp1score = updatedGame.Scrs[COMPETITOR_1_INDEX].toInt().toString()
                           comp2score = updatedGame.Scrs[COMPETITOR_2_INDEX].toInt().toString()
                       }
                       updatingGame?.apply {
                           cellType = GAME_TYPE
                           isGameStarted = isGameStarted
                           isGameActive = updatedGame.Active
                           gameTime = updatedGame.STime.formatDate(
                               SERVER_DATE_FORMAT,
                               CLIENT_DATE_FORMAT
                           )
                           gameComp1?.score = comp1score
                           gameComp2?.score = comp2score
                       }*/
                    checkedGamesList.add(outDatedGame)
                }


            }
        }

        return UIResponse(changedGamesIndexes, newUiGameDataCellsList)
    }


}



