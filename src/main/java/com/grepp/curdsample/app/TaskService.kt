package com.grepp.curdsample.app

import com.grepp.curdsample.dao.TaskRepository
import com.grepp.curdsample.domain.Task
import com.grepp.curdsample.domain.toDescription
import com.grepp.curdsample.domain.toDto
import com.grepp.curdsample.dto.TaskDescription
import com.grepp.curdsample.dto.TaskDto
import com.grepp.curdsample.dto.TaskPageDto
import com.grepp.curdsample.dto.toEntity
import com.grepp.curdsample.exception.TaskNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class TaskService(
    private val taskRepository: TaskRepository
) {

    fun getTasksDueToToday(): List<TaskDto> {
        return taskRepository.findTenTasksDueToToday().map { it.toDto() }
    }

    fun getTaskList(pageNum: Int): TaskPageDto {
        val SIZE = 5

        val pageReq = PageRequest.of(pageNum, SIZE, Sort.Direction.DESC, "createdAt")
        val tasks = taskRepository.findAll(pageReq)

        return TaskPageDto(
            hasNext = tasks.hasNext(),
            data = tasks.content.map { it.toDto() }
        )
    }

    @Transactional
    fun saveTask(taskDto: TaskDto): TaskDto {
        taskDto.code = genCode()

//        val task: Task = taskDto.toEntity()
        taskRepository.save(taskDto.toEntity())

        return taskDto
    }

    @Transactional
    fun checkTaskByCode(code: String): TaskDto {
        val findTask: Task = findByCode(code)
        findTask.updateCheck()

        return findTask.toDto()
    }

    @Transactional
    fun update(taskDto: TaskDto): TaskDto {
        val findTask = findByCode(taskDto.code)
        findTask.update(taskDto)

        return taskDto
    }

    private fun findByCode(code: String): Task {
        return taskRepository.findByCode(code)
            ?: throw TaskNotFoundException()
    }

    /**
     * 할일의 코드를 이용해서 [TaskDto]를 반환하는 메서드입니다.
     *
     * @param code 할일을 식별하기 위한 코드
     * @return [TaskDto]
     */
    fun getByCode(code: String): TaskDto {
        return findByCode(code).toDto()
    }
    //    fun getByCode(code: String?): TaskDto = findByCode(code).toDto()

    fun getDescriptionByCode(code: String): TaskDescription {
        return findByCode(code).toDescription()
    }

    @Transactional
    fun removeByCode(code: String): String {
        val findTask = findByCode(code)
        taskRepository.delete(findTask)

        return code
    }

    private fun genCode(): String {
        return UUID.randomUUID().toString()
    }
}
