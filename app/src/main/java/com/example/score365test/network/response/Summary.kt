package com.example.score365test.network.response

data class Summary(
    val Dates: List<Date>,
    val IncludesToday: Boolean,
    val RangeEnd: String,
    val RangeStart: String
)