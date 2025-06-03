package com.grepp.curdsample.dto

data class GeneralApiResponse<T>(
    val data: T? = null,
    val msg: String,
)
