package com.example.score365test.network.response

data class GamesDataResponse(
    val Bookmakers: List<Any>,
    val Competitions: ArrayList<Competition>,
    val Countries: List<Country>,
    val CurrentDate: String,
    val Games: ArrayList<GameX>,
    val LastUpdateID: String,
    val RequestedUpdateID: String,
    val ScrollIndex: Int,
    val Summary: Summary,
    val TTL: Int,
    val TVNetworks: List<TVNetworkX>
)