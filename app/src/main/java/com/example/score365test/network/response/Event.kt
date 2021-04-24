package com.example.score365test.network.response

data class Event(
    val AddedTime: Int,
    val AthleteID: Int,
    val Comp: Int,
    val EventOrder: Int,
    val ExtraAthletes: List<Int>,
    val ExtraPlayerIds: List<Int>,
    val ExtraPlayers: List<String>,
    val GT: Double,
    val GTD: String,
    val GameCompletion: Double,
    val IsDel: Boolean,
    val Num: Int,
    val PBPEventKey: String,
    val Player: String,
    val PlayerSName: String,
    val SType: Int,
    val Status: Int,
    val Type: Int
)