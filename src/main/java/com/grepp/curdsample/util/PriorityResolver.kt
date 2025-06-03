package com.grepp.curdsample.util

class PriorityResolver {
    fun resolve(priority: Int): String {
        return when (priority) {
            0 -> " "
            1 -> "text-red-700"
            2 -> "text-red-500"
            3 -> "text-yellow-500"
            4 -> "text-green-500"
            else -> "text-green-700"
        }
    }
}
