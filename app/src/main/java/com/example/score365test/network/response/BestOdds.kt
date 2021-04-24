package com.example.score365test.network.response

data class BestOdds(
    val Bookmakers: List<Bookmaker>,
    val Lines: List<Line>
)