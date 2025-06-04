package com.grepp.curdsample.dto

data class TaskDescription(
    val code: String,
    val title: String,
    val description: String,
    val priority: Int,
    val completeStatus: Boolean,
    val startDate: String,
    val dueDate: String,
    val createdAt: String,
    val updatedAt: String,
    val priorityLevel: String,
)