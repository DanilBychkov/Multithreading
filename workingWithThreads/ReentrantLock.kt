package workingWithThreads

import java.util.concurrent.locks.ReentrantLock


private interface IncrementReentrantLock {

    fun increaseAmount()

    fun getAmount(): Int
}

/**
 * Пример синхронизации потоков через класс ReentrantLock
 */
private class IncrementReentrantLockImpl : IncrementReentrantLock {

    private var amount: Int = 0
    private val lock: ReentrantLock = ReentrantLock()

    override fun increaseAmount() {
        println("HoldCount - " + lock.holdCount); //количество удержаний блокировки текущим потоком
        println("QueueLength - " + lock.queueLength); //длина очереди ожидания
        lock.lock()
        try {
            amount++
        } finally {
            lock.unlock()
        }

    }

    override fun getAmount() = amount
}

private class IncrementThreadReentrant(private val increment: IncrementReentrantLock) : Thread() {

    override fun run() {
        repeat(1000) {
            increment.increaseAmount()
        }
    }

}

fun main() {
    val increment = IncrementReentrantLockImpl()

    repeat(200) {
        IncrementThreadReentrant(increment).start()
    }

    Thread.sleep(5_000L)
    println(increment.getAmount())
}