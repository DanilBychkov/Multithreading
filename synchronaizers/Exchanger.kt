package synchronaizers

import java.util.concurrent.Exchanger
import kotlin.random.Random

/**
 * Пример использования [Exchanger]
 */

private class Waiter(private val exchanger: Exchanger<String>) : Runnable {

    override fun run() {
        repeat(10) {
            val currentMessage = exchanger.exchange("")
            println(currentMessage)
        }
    }
}

private class Cook(private val exchanger: Exchanger<String>) : Runnable {

    override fun run() {
        repeat(10) {
            Thread.sleep(1_000L)
            val dish = Random.nextInt(1, 100)
            exchanger.exchange("Я приготовил блюдо под номером $dish")
        }
    }
}

fun main() {
    val exchange = Exchanger<String>()
    Thread(Waiter(exchange)).start()
    Thread(Cook(exchange)).start()
}