package com.grepp.curdsample

import com.grepp.curdsample.GeneralApiResponse
import com.grepp.curdsample.TaskDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tasks")
class TaskApiController(
    private val taskService: TaskService
) {

    @PatchMapping("/{code}/status")
    fun updateTaskStatus(
        @PathVariable code: String
    ): GeneralApiResponse<TaskDto> {
        val taskDto = taskService.checkTaskByCode(code)
        return GeneralApiResponse(
            data = taskDto,
            msg = "성공적으로 반영되었습니다!",
        )
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTask(
        @PathVariable code: String
    ): GeneralApiResponse<Void> {
        taskService.removeByCode(code)
        return GeneralApiResponse(
            msg = "성공적으로 삭제되었습니다"
        )
    }

}