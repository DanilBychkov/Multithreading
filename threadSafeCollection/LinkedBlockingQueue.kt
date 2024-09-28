package threadSafeCollection

import java.lang.Thread.sleep
import java.util.concurrent.LinkedBlockingQueue

/**
 * Пример использования [LinkedBlockingQueue]
 */

fun main() {
    val queue = LinkedBlockingQueue<Int>(10)

    val hostesses = Thread {
        repeat(30){
            queue.put(it)
            println("Гость под номером $it пришел в ресторан")
            println("Свободных столиков ${queue.remainingCapacity()}")
            sleep(200L)
        }
    }
    val waiters = Thread {
        while (true){
            val visitor = queue.take()
            sleep(1_000L)
            println("Официант обслужил гостя под номером $visitor")
            println("Свободных столиков ${queue.remainingCapacity()}")
        }
    }

    hostesses.start()
    waiters.start()
}