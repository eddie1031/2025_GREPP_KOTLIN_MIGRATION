package com.grepp.curdsample.dto

import com.grepp.curdsample.domain.Task
import com.grepp.curdsample.util.PriorityResolver
import com.grepp.curdsample.util.TimeFormatter

data class TaskDescription(
    val code: String,
    val title: String,
    val description: String,
    val priority: Int,
    val completeStatus: Boolean,
    val startDate: String,
    val dueDate: String,
    val createdAt: String,
    val updatedAt: String
) {

    val priorityLevel: String
        get() = PriorityResolver.resolve(priority)


    companion object {
        @JvmStatic
        fun from(task: Task): TaskDescription {
            return TaskDescription(
                task.code,
                task.title,
                task.description,
                task.priority,
                task.completeStatus,
                TimeFormatter.convertToStr(task.startTime),
                TimeFormatter.convertToStr(task.endTime),
                TimeFormatter.convertToStr(task.createdAt),
                TimeFormatter.convertToStr(task.updatedAt)
            )
        }
    }
}
