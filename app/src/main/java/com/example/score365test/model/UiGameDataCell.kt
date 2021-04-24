package com.example.score365test.model

const val GAME_TYPE = 0
const val COMPETITION_TYPE = 1

data class UiGameDataCell(
    var id: Int? = null,
    var gameComp1: UiCompetitor? = null,
    var gameComp2: UiCompetitor? = null,
    var isGameStarted: Boolean? = null,
    var gameTime: String? = null,
    var cellType: Int,
    var competitionName: String? = null,
    var competitionFlagUrl: String? = null,
    var isGameActive: Boolean = false
)

data class UiCompetitor(val name: String, val imageUrl: String, var score: String)