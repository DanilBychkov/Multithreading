package workingWithThreads

/**
 * Пример того как может возникнуть DeadLock
 */

private class ThirstThread(private val left: Any, private val right: Any) : Thread() {
    override fun run() {
        while (true) {
            synchronized(left) {
                sleep(1_000L)
                synchronized(right) {
                    //do something
                }
            }
            println("First thread finished")
        }
    }
}


private class SecondThread(private val left: Any, private val right: Any) : Thread() {
    override fun run() {
        while (true) {
            synchronized(right) {
                sleep(1_000L)
                synchronized(left) {
                    //do something
                }
            }
            println("Second thread finished")
        }
    }
}

fun main() {
    val leftLock = Object()
    val rightLock = Object()

    val firstThread = ThirstThread(leftLock, rightLock)
    val secondThread = SecondThread(leftLock, rightLock)

    firstThread.start()
    secondThread.start()
}