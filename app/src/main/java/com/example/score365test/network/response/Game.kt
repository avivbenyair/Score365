package com.example.score365test.network.response

data class Game(
    val CompetitionID: Int,
    val Competitors: List<Competitor>,
    val GroupNum: Int,
    val Num: Int,
    val SeasonNum: Int,
    val SportTypeID: Int,
    val StageNum: Int,
    val StartTime: String,
    val StartTimeUTC: String,
    val UseName: Boolean
)