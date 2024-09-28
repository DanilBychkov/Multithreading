package workingWithThreads

/**
 * Ключевое слово volatile обозначает неблокирующий механизм синхронизированного доступа к полю.
 */
@Volatile
private var RUNNING = true

fun main() {
    val thread = Thread {
        while (RUNNING) {
            Thread.sleep(1_000L)
            println("Thread is running")
        }
    }
    thread.start()
    Thread.sleep(5_000L)
    RUNNING = false
    println("Rinning is false")
}