package com.grepp.curdsample.domain

import com.grepp.curdsample.dto.TaskDto
import com.grepp.curdsample.util.TimeFormatter
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
class Task(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Setter
    @Column(nullable = false, unique = true)
    var code: String? = null,

    var title: String? = null,

    var description: String? = null,

    var priority: Int? = null,

    var completeStatus: Boolean = false,

    var startTime: LocalDate? = null,

    var endTime: LocalDate? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now()

) {

    fun updateCheck() {
        this.completeStatus = !this.completeStatus
        this.updatedAt = LocalDateTime.now()
    }

    fun update(dto: TaskDto) {
        this.title = dto.title
        this.description = dto.description
        this.priority = dto.priority
        this.completeStatus = dto.completeStatus

        this.startTime = TimeFormatter.convertToLocalDate(dto.startTime)
        this.endTime = TimeFormatter.convertToLocalDate(dto.endTime)

        this.updatedAt = LocalDateTime.now()
    }

    companion object {
        @JvmStatic
        fun of(taskDto: TaskDto): Task {
            val task = Task()

            task.code = taskDto.code
            task.title = taskDto.title
            task.description = taskDto.description
            task.priority = taskDto.priority
            task.completeStatus = taskDto.completeStatus
            task.startTime = TimeFormatter.convertToLocalDate(taskDto.startTime)
            task.endTime = TimeFormatter.convertToLocalDate(taskDto.endTime)

            return task
        }
    }
}
