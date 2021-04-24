package com.example.score365test.network.response

data class Bookmaker(
    val ActionButton: ActionButton,
    val Color: String,
    val Disclaimer: Disclaimer,
    val ID: Int,
    val ImgVer: Int,
    val Name: String,
    val NameForURL: String,
    val ShowLinksInBrowser: Boolean,
    val Sponsored: Boolean,
    val URL: String,
    val UseDeepestLink: Boolean
)