package com.grepp.curdsample.dto

data class TaskPageDto(
    val hasNext: Boolean,
    val data: List<TaskDto>,
)
