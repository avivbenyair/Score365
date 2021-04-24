package com.example.score365test.network.response

data class Competition(
    val AutoProgressAddedTime: Boolean,
    val CID: Int,
    val Color: String,
    val Color2: String,
    val CompetitorsType: Int,
    val CurrSeason: Int,
    val CurrSeasonEnd: String,
    val CurrSeasonStart: String,
    val CurrStage: Int,
    val Expanded: Boolean,
    val FatherCompetition: Int,
    val GamesCount: Int,
    val Gender: Int,
    val HasLiveTable: Boolean,
    val HasLogo: Boolean,
    val HasSquads: Boolean,
    val HasTbl: Boolean,
    val HasTexture: Boolean,
    val HomeAwayTeamOrder: Int,
    val ID: Int,
    val ImgVer: Int,
    val LiveCount: Int,
    val Name: String,
    val NameForURL: String,
    val OrderLevel: Int,
    val PlayingCount: Int,
    val PopularityRank: Int,
    val SID: Int,
    val SName: String,
    val Seasons: List<Season>,
    val ShowTopAthletes: Boolean,
    val SupportMissingPlayers: Boolean,
    val TextColor: String
)