package com.example.score365test.network.response

data class Group(
    val Expanded: Boolean,
    val Games: List<Game>,
    val GamesCount: Int,
    val GroupBy: Boolean,
    val HasTbl: Boolean,
    val LiveCount: Int,
    val Name: String,
    val Num: Int,
    val Participants: List<Participant>,
    val ShowGames: Boolean,
    val UseName: Boolean
)