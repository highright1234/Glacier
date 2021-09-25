package com.github.highright1234.glacier.scheduler

import java.lang.Runnable

data class GlacierTask(val task : Runnable, val id : Int = 0) : Runnable {

    val createdAt = System.nanoTime()

    override fun run() {
        task.run()
    }
}