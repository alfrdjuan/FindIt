package com.codewithjun.findit.network.model

data class AutocompleteResult(
    val predictions: List<Prediction>,
    val status: String
)