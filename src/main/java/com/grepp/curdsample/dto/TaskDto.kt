package com.grepp.curdsample.dto

import com.grepp.curdsample.domain.Task
import com.grepp.curdsample.util.convertToLocalDate
import com.grepp.curdsample.util.priorityResolve
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class TaskDto(
    var code: String = "",

    @field: NotBlank(message = "작업 이름은 반드시 입력되어야 합니다")
    val title: String = "",

    val description: String = "",

    @field: Min(value = 0)
    val priority: Int = -1,
    val priorityLevel: String = "",

    val completeStatus: Boolean = false,

    @field: Pattern(
        regexp = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$",
        message = "올바른 날짜를 입력하여 주시기 바랍니다."
    )
    @field: NotBlank(message = "날짜는 반드시 들어있어야 합니다")
    val startTime: String = "",

    @field: Pattern(
        regexp = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$",
        message = "올바른 날짜를 입력하여 주시기 바랍니다."
    )
    @field: NotBlank(message = "날짜는 반드시 들어있어야 합니다")
    val endTime: String = ""
)

fun TaskDto.toEntity(): Task {
    return Task(
        code = this.code,
        title = this.title,
        description = this.description,
        priority = this.priority,
        completeStatus = this.completeStatus,
        startTime = convertToLocalDate(this.startTime),
        endTime = convertToLocalDate(this.endTime),
    )
}