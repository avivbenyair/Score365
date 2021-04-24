package com.example.score365test.network.response

data class Line(
    val BMGID: Int,
    val BMID: Int,
    val BookPos: Int,
    val EntID: Int,
    val ID: Int,
    val Link: String,
    val Options: List<Option>,
    val Sponsored: Boolean,
    val TrackingURL: String,
    val Type: Int
)