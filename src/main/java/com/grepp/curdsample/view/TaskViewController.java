package com.grepp.curdsample.view;

import com.grepp.curdsample.app.TaskService;
import com.grepp.curdsample.dto.TaskDescription;
import com.grepp.curdsample.dto.TaskDto;
import com.grepp.curdsample.dto.TaskPageDto;
import com.grepp.curdsample.dto.TodayTaskDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class TaskViewController {

    private final TaskService taskService;

    @GetMapping("/")
    public String showIndex(Model model) {
        List<TaskDto> result = taskService.getTasksDutToToday();
        model.addAttribute("tasks", TodayTaskDto.from(result));
        return "index";
    }

    @GetMapping("/tasks")
    public String showList(Model model, Pageable pageable) {
        TaskPageDto tasks = taskService.getTaskList(pageable.getPageNumber());
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/tasks/more") //
    public String loadMoreTasks(int page, Model model) {
        TaskPageDto taskList = taskService.getTaskList(page);
        model.addAttribute("tasks", taskList);
        return "fragments/page_parts :: taskPagePart";
    }

    @GetMapping("/tasks/append")
    public String showAddPage(Model model) {
//        model.addAttribute("taskDto", new TaskDto());
        return "tasks/add";
    }

    @PostMapping("/tasks/append")
    public String addTask(@Valid TaskDto req, BindingResult bindingResult, Model model) {
        log.info("req = {}", req.toString());

        if ( bindingResult.hasErrors() ) {
            model.addAttribute("taskDto", req);
            log.info("입력이 잘못되어 오류페이지로 이동되었음, {}", req.toString());
            return "tasks/add";
        }

        TaskDto taskDto = taskService.saveTask(req);
        log.info("taskDto = {}", taskDto);

        return "redirect:/tasks/append";
    }


    @GetMapping("/tasks/{code}")
    public String showTaskDetail(
            @PathVariable(name = "code") String code,
            Model model
    ) {
        TaskDescription description = taskService.getDescriptionByCode(code);
        log.info("description = {}", description);
        model.addAttribute("task", description);
        return "tasks/detail";
    }

    @GetMapping("/tasks/{code}/edit")
    public String showEditPage(@PathVariable String code, Model model) {

        TaskDto task = taskService.getByCode(code);
        model.addAttribute("task", task);

        return "tasks/edit";
    }

    @PostMapping("/tasks/{code}/edit")
    public String updateTask(@PathVariable String code
            , @Valid TaskDto updateReq) {

        updateReq.setCode(code);
        taskService.update(updateReq);

        return "redirect:/tasks/" + code;
    }


}
