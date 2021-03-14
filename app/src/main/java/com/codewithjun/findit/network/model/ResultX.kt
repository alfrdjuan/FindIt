package com.codewithjun.findit.network.model

data class ResultX(
    val address_components: List<AddressComponent>,
    val formatted_address: String,
    val geometry: Geometry,
    val name: String
)