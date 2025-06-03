package com.grepp.curdsample.dto;

import com.grepp.curdsample.domain.Task;
import com.grepp.curdsample.util.PriorityResolver;
import com.grepp.curdsample.util.TimeFormatter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String code;

    @NotBlank(message = "작업 이름은 반드시 입력되어야 합니다")
    private String title;

    private String description;

    @Min(value = 0)
    private Integer priority;

    private boolean completeStatus;

    @NotBlank(message = "날짜는 반드시 들어있어야 합니다")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$", message = "올바른 날짜를 입력하여 주시기 바랍니다.")
    private String startTime;

    @NotBlank(message = "날짜는 반드시 들어있어야 합니다")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$", message = "올바른 날짜를 입력하여 주시기 바랍니다.")
    private String endTime;

    public static TaskDto from(Task task) {

        TaskDto taskDto = new TaskDto();

        taskDto.code = task.getCode();
        taskDto.title = task.getTitle();
        taskDto.description = task.getDescription();
        taskDto.priority = task.getPriority();
        taskDto.completeStatus = task.isCompleteStatus();
        taskDto.startTime = TimeFormatter.convertToStr(task.getStartTime());
        taskDto.endTime = TimeFormatter.convertToStr(task.getEndTime());

        return taskDto;

    }

    public String getPriorityLevel() {
        return PriorityResolver.resolve(priority);
    }


}
