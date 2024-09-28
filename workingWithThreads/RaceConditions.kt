package workingWithThreads

import java.util.concurrent.atomic.AtomicInteger

private interface Increment {

    fun increaseAmount()

    fun getAmount(): Int
}

/**
 * Пример синхронизации потоков через метод synchronized
 */
private class IncrementSynchronized : Increment {

    private var amount: Int = 0

    override fun increaseAmount() {
        synchronized(this) {
            amount++
        }
    }

    override fun getAmount() = amount
}

/**
 * Пример синхронизации потоков используя Atomic
 */
private class IncrementAtomic : Increment {
    private var amount: AtomicInteger = AtomicInteger(0)

    override fun increaseAmount() {
        amount.incrementAndGet()
    }

    override fun getAmount() = amount.get()

}

private class IncrementThread(private val increment: Increment) : Thread() {

    override fun run() {
        repeat(1000) {
            increment.increaseAmount()
        }
    }

}

private fun example1() {
    val increment = IncrementAtomic()

    repeat(200) {
        IncrementThread(increment).start()
    }

    Thread.sleep(5_000L)
    println(increment.getAmount())
}

private fun example2(){
    val increment = IncrementSynchronized()

    repeat(200) {
        IncrementThread(increment).start()
    }

    Thread.sleep(5_000L)
    println(increment.getAmount())
}

fun main() {
    example1()
}