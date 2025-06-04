package com.grepp.curdsample

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

private val log = LoggerFactory.getLogger(TaskViewController::class.java)

@Controller
class TaskViewController(
    private val taskService: TaskService
) {

    @GetMapping("/")
    fun showIndex(model: Model): String {
        val result: List<TaskDto> = taskService.getTasksDueToToday()
        model.addAttribute("tasks", result.toTodayTasks())
//        model["tasks"] = result.toTodayTasks()
        return "index"
    }

    @GetMapping("/tasks")
    fun showList(model: Model, pageable: Pageable): String {
        model["tasks"] = taskService.getTaskList(pageable.pageNumber)
        return "tasks/list"
    }

    @GetMapping("/tasks/more")
    fun loadMoreTasks(page: Int, model: Model): String {
        model["tasks"] = taskService.getTaskList(page)
        return "fragments/page_parts :: taskPagePart"
    }

    @GetMapping("/tasks/append")
    fun showAddPage(model: Model): String {
        model["taskDto"] = TaskDto()
        return "tasks/add"
    }

    @PostMapping("/tasks/append")
    fun addTask(req: @Valid TaskDto, bindingResult: BindingResult, model: Model): String {
        log.info("req = {}", req.toString())

        if (bindingResult.hasErrors()) {
            model["taskDto"] = req
            log.info("입력이 잘못되어 오류페이지로 이동되었음, {}", req.toString())
            return "tasks/add"
        }

        val taskDto = taskService.saveTask(req)
        log.info("taskDto = {}", taskDto)

        return "redirect:/tasks/append"
    }


    @GetMapping("/tasks/{code}")
    fun showTaskDetail(
        @PathVariable code: String,
        model: Model,
    ): String {
        model["task"] = taskService.getDescriptionByCode(code)
        return "tasks/detail"
    }

    @GetMapping("/tasks/{code}/edit")
    fun showEditPage(
        @PathVariable code: String,
        model: Model,
    ): String {
        model["task"] = taskService.getByCode(code)
        return "tasks/edit"
    }

    @PostMapping("/tasks/{code}/edit")
    fun updateTask(
        @PathVariable code: String,
        updateReq: @Valid TaskDto
    ): String {
        updateReq.code = code
        taskService.update(updateReq)
        return "redirect:/tasks/$code"
    }
}