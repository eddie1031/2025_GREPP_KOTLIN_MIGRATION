package com.grepp.curdsample.app;

import com.grepp.curdsample.dao.TaskRepository;
import com.grepp.curdsample.domain.Task;
import com.grepp.curdsample.dto.TaskDescription;
import com.grepp.curdsample.dto.TaskDto;
import com.grepp.curdsample.dto.TaskPageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public TaskDto checkTaskByCode(String code) {

        Task findTask = findByCode(code);
        findTask.updateCheck();

        return TaskDto.from(findTask);
    }


    public TaskPageDto getTaskList(int pageNum) {

        final int SIZE = 5;

        PageRequest pageReq = PageRequest.of(pageNum, SIZE, Sort.Direction.DESC, "createdAt");
        Page<Task> tasks = taskRepository.findAll(pageReq);

        return TaskPageDto.builder()
                .hasNext(tasks.hasNext())
                .data(tasks.stream().map(TaskDto::from).toList())
                .build();

//        return new TaskPageDto(
//                tasks.hasNext(),
//                tasks.stream().map(TaskDto::from)
//                    .toList()
//        );

    }

    public List<TaskDto> getTasksDutToToday() {
        return taskRepository.findTenTasksDueToToday()
                .stream()
                .map(TaskDto::from)
                .toList();
    }

    public TaskDescription getDescriptionByCode(String code) {
        return TaskDescription.from(findByCode(code));
    }

    @Transactional
    public TaskDto saveTask(TaskDto taskDto) {
        taskDto.setCode(genCode());
        Task task = Task.of(taskDto);
        taskRepository.save(task);
        return taskDto;
    }

    private Task findByCode(String code) {
        return taskRepository.findByCode(code).orElse(null);
    }

    private String genCode() {
        return UUID.randomUUID().toString();
    }

    /**
     * 할일의 코드를 이용해서 {@link TaskDto}를 반환하는 메서드입니다.
     *
     * @param code 할일을 식별하기 위한 코드
     * @return {@link TaskDto}
     */
    public TaskDto getByCode(String code) {
        Task findTask = findByCode(code);
        return TaskDto.from(findTask);
    }

    @Transactional
    public String removeByCode(String code) {

        Task findTask = findByCode(code);
        taskRepository.delete(findTask);

        return code;
    }

    @Transactional
    public TaskDto update(TaskDto taskDto) {

        Task findTask = findByCode(taskDto.getCode());
        findTask.update(taskDto);

        return taskDto;
    }


}
