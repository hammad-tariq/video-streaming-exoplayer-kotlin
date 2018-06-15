package com.intigral.assignmenthammad.data.api.pojo.response


data class Player(
    val Order: Int,
    val StartInField: Boolean,
    val Role: String,
    val IsCaptain: Boolean,
    val JerseyNumber: String,
    val Id: Int,
    val Name: String,
    val XCoordinate: Int,
    val YCoordinate: Int
)