package com.grepp.curdsample.dto

import com.grepp.curdsample.domain.Task
import com.grepp.curdsample.util.PriorityResolver
import com.grepp.curdsample.util.TimeFormatter
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

data class TaskDto(
    var code: String? = null,

    @field: NotBlank(message = "작업 이름은 반드시 입력되어야 합니다")
    var title:  String? = null,

    var description: String? = null,

    @field: Min(value = 0)
    var priority:  Int? = null,

    var completeStatus: Boolean = false,

    @field: Pattern(
        regexp = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$",
        message = "올바른 날짜를 입력하여 주시기 바랍니다."
    )
    @field: NotBlank(message = "날짜는 반드시 들어있어야 합니다")
    var startTime:   String? = null,

    @field: Pattern(
        regexp = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$",
        message = "올바른 날짜를 입력하여 주시기 바랍니다."
    )
    @field: NotBlank(message = "날짜는 반드시 들어있어야 합니다")
    var endTime:   String? = null

) {

    val priorityLevel: String
        get() = PriorityResolver.resolve(priority)


    companion object {
        @JvmStatic
        fun from(task: Task): TaskDto {
            val taskDto = TaskDto()

            taskDto.code = task.code
            taskDto.title = task.title
            taskDto.description = task.description
            taskDto.priority = task.priority
            taskDto.completeStatus = task.completeStatus
            taskDto.startTime = TimeFormatter.convertToStr(task.startTime)
            taskDto.endTime = TimeFormatter.convertToStr(task.endTime)

            return taskDto
        }
    }
}
