package workingWithThreads

import java.util.Objects

private class MyThread : Runnable {

    override fun run() {
        val name = Thread.currentThread().name
        repeat(5) {
            println("$name is running! ${it + 1}")
        }
        println("$name completed!")
    }
}

/**
 * Пример использования join, данный метод приостанавливает выполнения потока, пока не выполнится другой.
 */
private fun example1() {
    val firstThread = Thread(MyThread(), "First")
    val secondThread = Thread(MyThread(), "Second")
    val thirdThread = Thread(MyThread(), "Third")

    firstThread.start()
    firstThread.join()
    secondThread.start()
    secondThread.join()
    thirdThread.start()
}

private class CustomThread : Runnable {
    override fun run() {
        repeat(5) {
            println("${Thread.currentThread().name} $it")
            Thread.yield()
        }
    }
}

/**
 * Пример использования yield, данный метод возвращает поток в статус runnable,
 * для того чтобы уступить место другому потоку.
 */
private fun example2() {
    val firstThread = Thread(CustomThread(), "First")
    val secondThread = Thread(CustomThread(), "Second")
    val thirdThread = Thread(CustomThread(), "Third")

    firstThread.start()
    secondThread.start()
    thirdThread.start()
}

private class Message {
    var value: String = ""
}

fun main() {
    example2()
}