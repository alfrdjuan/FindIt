package com.codewithjun.findit.network.model

data class GeocodeResult(
    val plus_code: PlusCode,
    val results: List<Result>,
    val status: String
)