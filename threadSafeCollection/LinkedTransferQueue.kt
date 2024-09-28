package threadSafeCollection

import java.util.concurrent.ConcurrentSkipListSet
import java.util.concurrent.LinkedTransferQueue

/**
 * Пример работы [LinkedTransferQueue]
 */

fun main() {
    val queue = LinkedTransferQueue<Int>()

    ConcurrentSkipListSet<Int>()

    val waiter = Thread {
        while (!Thread.currentThread().isInterrupted) {
            val id = queue.take()
            println("Официант начал обслуживание гостя $id")
            try {
                Thread.sleep(1_000L)
            } catch (e:InterruptedException){
                Thread.currentThread().interrupt()
            }
            println("Официант закончил обслуживание гостя $id")
        }
    }

    val hosts = Thread {
        repeat(30) {
            Thread.sleep(100L)
            println("Хостес пригласил гостя под номером $it")
            println("Свободный столиков ${queue.waitingConsumerCount}")
            queue.transfer(it)
        }
        waiter.interrupt()
    }

    waiter.start()
    hosts.start()
}