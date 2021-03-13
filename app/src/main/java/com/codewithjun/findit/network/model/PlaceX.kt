package com.codewithjun.findit.network.model

data class PlaceX(
    val plus_code: PlusCode,
    val results: List<Result>,
    val status: String
)