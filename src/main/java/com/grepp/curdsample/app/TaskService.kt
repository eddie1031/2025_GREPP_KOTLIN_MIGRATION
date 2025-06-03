package com.grepp.curdsample.app

import com.grepp.curdsample.dao.TaskRepository
import com.grepp.curdsample.domain.Task
import com.grepp.curdsample.domain.Task.Companion.of
import com.grepp.curdsample.domain.toDto
import com.grepp.curdsample.dto.TaskDescription
import com.grepp.curdsample.dto.TaskDto
import com.grepp.curdsample.dto.TaskPageDto
import com.grepp.curdsample.dto.toEntity
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
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

    fun getDescriptionByCode(code: String?): TaskDescription {
        return TaskDescription.from(findByCode(code))
    }

    private fun findByCode(code: String?): Task {
        return taskRepository!!.findByCode(code)!!.orElse(null)!!
    }

    private fun genCode(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * 할일의 코드를 이용해서 [TaskDto]를 반환하는 메서드입니다.
     *
     * @param code 할일을 식별하기 위한 코드
     * @return [TaskDto]
     */
    fun getByCode(code: String?): TaskDto {
        val findTask = findByCode(code)
        return TaskDto.from(findTask)
    }

    @Transactional
    fun removeByCode(code: String?): String? {
        val findTask = findByCode(code)
        taskRepository!!.delete(findTask)

        return code
    }
}
