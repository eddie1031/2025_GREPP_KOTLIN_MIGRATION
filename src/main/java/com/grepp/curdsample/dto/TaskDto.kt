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

@Data
@NoArgsConstructor
@AllArgsConstructor
class TaskDto {
    private var code: String? = null

    private var title: @NotBlank(message = "작업 이름은 반드시 입력되어야 합니다") String? = null

    private var description: String? = null

    private var priority: @Min(value = 0) Int? = null

    private var completeStatus = false

    private var startTime: @NotBlank(message = "날짜는 반드시 들어있어야 합니다") @Pattern(
        regexp = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$",
        message = "올바른 날짜를 입력하여 주시기 바랍니다."
    ) String? = null

    private var endTime: @NotBlank(message = "날짜는 반드시 들어있어야 합니다") @Pattern(
        regexp = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$",
        message = "올바른 날짜를 입력하여 주시기 바랍니다."
    ) String? = null

    val priorityLevel: String
        get() = PriorityResolver.resolve(priority)


    companion object {
        @JvmStatic
        fun from(task: Task): TaskDto {
            val taskDto = TaskDto()

            taskDto.code = task.getCode()
            taskDto.title = task.getTitle()
            taskDto.description = task.getDescription()
            taskDto.priority = task.getPriority()
            taskDto.completeStatus = task.isCompleteStatus()
            taskDto.startTime = TimeFormatter.convertToStr(task.getStartTime())
            taskDto.endTime = TimeFormatter.convertToStr(task.getEndTime())

            return taskDto
        }
    }
}
