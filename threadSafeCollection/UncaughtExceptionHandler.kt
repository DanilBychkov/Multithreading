package threadSafeCollection

/**
 * Пример создания [Thread.UncaughtExceptionHandler]
 */
private class ExceptionHandler : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        println("Exception $e was caught")
    }
}

fun main() {
    val exceptionHandler = ExceptionHandler()
    val thread = Thread {
        throw RuntimeException("Error")
    }.apply {
        uncaughtExceptionHandler = exceptionHandler
    }
    thread.start()
}