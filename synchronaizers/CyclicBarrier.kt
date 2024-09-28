package synchronaizers

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.TimeUnit

fun main() {
    val barrier = CyclicBarrier(3) {
        println("Произошла отгрузка контейнеров, корабль отплывает!")
        try {
            Thread.sleep(1_000L)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    repeat(30) {
        Thread {
            barrier.await() //Имитируем загрузку контейнера на корабль, вместительность 3
        }.start()
    }

}