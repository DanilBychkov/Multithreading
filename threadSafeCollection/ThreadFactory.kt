package threadSafeCollection

import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

/**
 * Пример создания фабрики потоков
 */
private class Factory : ThreadFactory {
    override fun newThread(r: Runnable): Thread {
        return Thread(r).apply {
            priority = 7
            name = "CustomThread"
        }
    }
}


fun main() {
    val factory = Factory()
    val thread = Executors.newSingleThreadExecutor(factory)
    thread.submit {
        println(Thread.currentThread().name)
        //CustomThread
    }
    thread.shutdown()
}