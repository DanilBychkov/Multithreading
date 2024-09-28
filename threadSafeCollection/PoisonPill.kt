package threadSafeCollection

import java.util.ArrayList
import java.util.Queue
import java.util.concurrent.LinkedTransferQueue

/**
 * Пример работы паттерна PoisonPill
 */

private const val POISON = -1

fun main() {
    val queue = LinkedTransferQueue<Int>()
    val firstThread = Thread {
        while (true) {
            if (queue.take() == POISON) {
                break
            }
        }
        println("Поток 1 остановился")
    }
    val secondThread = Thread {
        repeat(5) {
            queue.transfer(it)
        }
        println("Пилюля отправлена")
        queue.transfer(POISON)
    }

    firstThread.start()
    secondThread.start()
}