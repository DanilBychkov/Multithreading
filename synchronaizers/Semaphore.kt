package synchronaizers

import java.util.concurrent.Semaphore

/**
 * Пример использования класса Semaphore
 */

private class Client(private val semaphore: Semaphore, private val number: Int) : Runnable {

    override fun run() {
        try {
            semaphore.acquire()
            println("Клиент под номером $number начал стрижку")
            Thread.sleep(1_500L)
            semaphore.release()
            println("Клиент под номером $number закончил стрижку")

        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

}

fun main() {
    val semaphore = Semaphore(3, true)
    repeat(10) {
        Thread(Client(semaphore, it)).start()
        Thread.sleep(300L)
    }
}