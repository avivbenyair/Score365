package com.example.score365test.network.response

data class Stage(
    val GamesCount: Int,
    val Groups: List<Group>,
    val HasTbl: Boolean,
    val IsFinal: Boolean,
    val LiveCount: Int,
    val Name: String,
    val Num: Int,
    val UseName: Boolean
)