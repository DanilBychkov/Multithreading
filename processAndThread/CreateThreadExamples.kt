package processAndThread

import kotlin.concurrent.thread

/**
 * Пример создания потока
 */
private fun example1() {
    val newThread = Thread {
        println("Hello world!")
    }.apply {
        priority = 8
    }

    newThread.start()
}

/**
 * Получение ссылки на текущий поток
 */
private fun example2() {
    println(Thread.currentThread())
}

private fun example3() {
    thread {
        val threadName = Thread.currentThread().name
        println(threadName)
    }
}

fun main() {
    example3()
}