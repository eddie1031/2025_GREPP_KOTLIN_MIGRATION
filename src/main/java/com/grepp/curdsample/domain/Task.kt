package com.grepp.curdsample.domain

import com.grepp.curdsample.dto.TaskDto
import com.grepp.curdsample.util.TimeFormatter
import jakarta.persistence.*
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
    val code: String,

    var title: String,

    var description: String,

    var priority: Int,

    var completeStatus: Boolean,

    var startTime: LocalDate,

    var endTime: LocalDate,

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

}

fun Task.toDto(): TaskDto {
    return TaskDto(
        code = this.code,
        title = this.title,
        description = this.description,
        priority = this.priority,
        completeStatus = this.completeStatus,
        startTime = TimeFormatter.convertToStr(this.startTime),
        endTime = TimeFormatter.convertToStr(this.endTime),
    )
}
