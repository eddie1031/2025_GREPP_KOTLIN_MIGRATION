package com.grepp.curdsample

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TaskRepository : JpaRepository<Task, Long> {
    fun findByCode(code: String?): Task?

    @Query("""
        select t 
        from 
            Task t 
        where 
            t.endTime = CURRENT_DATE 
        order by 
            t.priority desc 
        limit 
            10
        """)
    fun findTenTasksDueToToday(): List<Task>

}