package processAndThread

import kotlin.concurrent.thread

private class RunnableTest : Runnable {

    private var index: Int = 0
    private var isActive = true

    fun disabled() {
        isActive = false
    }

    override fun run() {
        while (isActive) {
            Thread.sleep(1_000L)
            println(index++)
        }
        println("Я закончил!")
    }
}

private fun example1() {
    val test = RunnableTest()
    val thread = Thread(test)
    thread.start()
    Thread.sleep(10_000L)
    test.disabled()
}

private fun sleep(delay: Long) {
    try {
        Thread.sleep(delay)
    } catch (e: InterruptedException) {
        Thread.currentThread().interrupt()
    }
}

private fun example2() {
    val thread = Thread {
        while (!Thread.currentThread().isInterrupted) {
            sleep(1_000L)
        }
        println("Thread finished")
    }

    thread.start()
    Thread.sleep(5_000L)
    thread.interrupt()
}

fun main() {
    example2()
}