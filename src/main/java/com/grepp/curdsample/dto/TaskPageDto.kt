package com.grepp.curdsample.dto

data class TaskPageDto(
    val hasNext: Boolean,
    val data: MutableList<TaskDto> = mutableListOf(),
)
