package processAndThread

/**
 * Поток-демон завершится когда завершится главный поток
 */
private fun example1() {
    val thread = Thread {
        repeat(1000) {
            println(it)
            Thread.sleep(1000L)
        }
    }.apply { isDaemon = true }
    thread.start()

    Thread.sleep(20_000L)
    println("${Thread.currentThread()} finished!")
}

fun main() {
    example1()
}