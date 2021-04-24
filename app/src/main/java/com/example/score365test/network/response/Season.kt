package com.example.score365test.network.response

data class Season(
    val End: String,
    val HasTbl: Boolean,
    val Name: String,
    val Num: Int,
    val Stages: List<Stage>,
    val Start: String,
    val UseName: Boolean
)