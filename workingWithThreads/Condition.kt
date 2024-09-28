package workingWithThreads

import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock


private class Table {

    private val reentrantLock = ReentrantLock()
    private val condition = reentrantLock.newCondition()
    private var dishedCount = 0

    fun getDish() {
        reentrantLock.lock()
        try {
            if (dishedCount >= 0) {
                dishedCount -= 1
                condition.signal()
            }
        } finally {
            reentrantLock.unlock()
        }
    }

    fun addNewDish() {
        reentrantLock.lock()
        try {
            dishedCount += 1
            if (dishedCount >= 3) {
                println("Достигнуто максимальное количество блюд, ждем пока заберут")
                condition.await()
            }
        } finally {
            reentrantLock.unlock()
        }
    }
}

private class Waiter(private val table: Table) : Thread() {

    override fun run() {
        while (true) {
            sleep(10_000L)
            table.getDish()
            println("Офицант забрал блюдо")
        }
    }
}

private class Cooker(private val table: Table) : Thread() {

    override fun run() {
        while (true) {
            sleep(1_000L)
            println("Повар приготовил блюдо")
            table.addNewDish()
            println("Повар поставил на стол")
        }
    }
}

fun main() {
    val table = Table()
    val waiter = Waiter(table)
    val cooker = Cooker(table)

    waiter.start()
    cooker.start()
}
