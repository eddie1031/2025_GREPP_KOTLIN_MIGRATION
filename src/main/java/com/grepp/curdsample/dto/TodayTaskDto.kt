package com.grepp.curdsample.dto

import java.util.stream.Collectors

data class TodayTaskDto(
    val uncompletedTasks: MutableList<TaskDto?>? = ArrayList<TaskDto?>(),
    val completedTasks: MutableList<TaskDto?>? = ArrayList<TaskDto?>()
) {

    companion object {
        fun from(tasks: MutableList<TaskDto?>): TodayTaskDto {
            val result = tasks.stream()
                .collect(
                    Collectors.partitioningBy(TaskDto::completeStatus)
                )
            return TodayTaskDto(
                result[false],
                result[true]
            )
        }
    }

}
