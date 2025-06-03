package com.grepp.curdsample.domain;

import com.grepp.curdsample.dto.TaskDto;
import com.grepp.curdsample.util.TimeFormatter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tasks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

    @Id
//    @Column(name = "tasks_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String code;

    private String title;
    private String description;

    private Integer priority;

//    @Setter
    private boolean completeStatus = false;

    private LocalDate startTime;
    private LocalDate endTime;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public void updateCheck() {
        this.completeStatus = !this.completeStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void update(TaskDto dto) {

        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.priority = dto.getPriority();
        this.completeStatus = dto.isCompleteStatus();

        this.startTime = TimeFormatter.convertToLocalDate(dto.getStartTime());
        this.endTime = TimeFormatter.convertToLocalDate(dto.getEndTime());

        this.updatedAt = LocalDateTime.now();

    }

    public static Task of(TaskDto taskDto) {
        Task task = new Task();

        task.code = taskDto.getCode();
        task.title = taskDto.getTitle();
        task.description = taskDto.getDescription();
        task.priority = taskDto.getPriority();
        task.completeStatus = taskDto.isCompleteStatus();
        task.startTime = TimeFormatter.convertToLocalDate(taskDto.getStartTime());
        task.endTime = TimeFormatter.convertToLocalDate(taskDto.getEndTime());

        return task;
    }


}
