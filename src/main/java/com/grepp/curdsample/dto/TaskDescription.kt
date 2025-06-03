package com.grepp.curdsample.dto

import com.grepp.curdsample.domain.Task
import com.grepp.curdsample.util.PriorityResolver
import com.grepp.curdsample.util.TimeFormatter
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

data class TaskDescription(
    var code: String? = null,
    var title: String? = null,
    var description: String? = null,
    var priority: Int? = null,
    var completeStatus: Boolean = false,
    var startDate: String? = null,
    var dueDate: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null
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
