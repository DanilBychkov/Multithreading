package synchronaizers

import java.util.concurrent.CountDownLatch

/**
 * Пример использования [CountDownLatch]
 */

fun main() {
    val launch = CountDownLatch(15)
    repeat(16) {
        Thread {
            println("Пользователь $it пришел на онлайн курс")
            launch.countDown()
            println("Пользователь $it ждет пока наберется 15 студентов")
            launch.await()
            println("Пользователь $it начал занятия")
        }.start()
        Thread.sleep(500L)
    }
}