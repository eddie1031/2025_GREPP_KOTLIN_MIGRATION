package com.grepp.curdsample

fun Task.toDto(): TaskDto {
    return TaskDto(
        code = this.code,
        title = this.title,
        description = this.description,
        priority = this.priority,
        completeStatus = this.completeStatus,
        startTime = convertToStr(this.startTime),
        endTime = convertToStr(this.endTime),
        priorityLevel = priorityResolve(this.priority),
    )
}

fun Task.toDescription(): TaskDescription {
    return TaskDescription(
        code = this.code,
        title = this.title,
        description = this.description,
        priority = this.priority,
        completeStatus = this.completeStatus,
        startDate = convertToStr(this.startTime),
        dueDate = convertToStr(this.endTime),
        createdAt = convertToStr(this.createdAt),
        updatedAt = convertToStr(this.updatedAt),
        priorityLevel = priorityResolve(this.priority)
    )
}

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

fun List<TaskDto>.toTodayTasks(): TodayTaskDto {

    val (completed, unCompleted) = partition { it.completeStatus }

    return TodayTaskDto(
        completedTasks = completed,
        uncompletedTasks = unCompleted
    )

}