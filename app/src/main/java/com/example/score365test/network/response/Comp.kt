package com.example.score365test.network.response

data class Comp(
    val AwayColor: String,
    val AwayColor2: String,
    val CID: Int,
    val Color: String,
    val Color2: String,
    val CompetitionHasTexture: Boolean,
    val HasSquad: Boolean,
    val ID: Int,
    val ImgVer: Int,
    val MainComp: Int,
    val Name: String,
    val NameForURL: String,
    val PopularityRank: Int,
    val SID: Int,
    val SName: String,
    val SymbolicName: String,
    val TextColor: String,
    val Trend: List<Int>,
    val Type: Int
)