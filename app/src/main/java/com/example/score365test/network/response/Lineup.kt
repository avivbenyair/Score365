package com.example.score365test.network.response

data class Lineup(
    val CompNum: Int,
    val DoubtfulTitle: String,
    val HasFieldPositions: Boolean,
    val HasRankings: Boolean,
    val Players: List<Player>
)