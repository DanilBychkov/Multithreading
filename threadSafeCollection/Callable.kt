package threadSafeCollection

import java.util.concurrent.Callable
import java.util.concurrent.FutureTask

/**
 * Пример использования интерфейса Callable и класса FutureTask
 */

fun main() {
    val task = Callable { "Done" }
    val future = FutureTask(task)

    val thread = Thread(future)
    thread.start()

    println(future.get())
}