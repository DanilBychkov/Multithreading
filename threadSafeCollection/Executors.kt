package threadSafeCollection

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Пример создания фиксированного пула потоков
 */
private fun example1() {
    val fixedPool = Executors.newFixedThreadPool(2)
    repeat(10) {
        fixedPool.submit {
            println("Я родился $it !")
            Thread.sleep(2_000L)
            println("Я помер $it (")
        }
    }
    fixedPool.shutdown()
}

/**
 * Пример создания запланированного пула потоков
 */
private fun example2() {
    val scheduledPool = Executors.newScheduledThreadPool(2)
    val task = Runnable {
        println("Alarm!")
    }
    scheduledPool.schedule(task, 3L, TimeUnit.SECONDS)
    scheduledPool.shutdown()
}

/**
 * Пример создания запланированного пула потока с вызовом каждые 3 секунды
 */
private fun example3() {
    val scheduledPool = Executors.newScheduledThreadPool(2)
    val task = Runnable {
        println("Alarm!")
    }
    scheduledPool.scheduleAtFixedRate(task, 0L, 3L, TimeUnit.SECONDS)
    Thread.sleep(10_000L)
    scheduledPool.shutdown()
}

private data class Person(val age:Int)
private fun Person.isAdult():Boolean = age >= 18

fun main() {
    val predicate = Person::isAdult
    val age= Person::age
    example3()
}