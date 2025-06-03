package com.grepp.curdsample.dto

data class TodayTaskDto(
    val uncompletedTasks: List<TaskDto>,
    val completedTasks: List<TaskDto>,
)

fun List<TaskDto>.toTodayTasks(): TodayTaskDto {

    val (completed, unCompleted) = partition { it.completeStatus }

    return TodayTaskDto(
        completedTasks = completed,
        uncompletedTasks = unCompleted
    )

}

