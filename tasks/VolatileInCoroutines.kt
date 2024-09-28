package tasks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * Если убрать аннотацию Volatile, то программа не завершится.
 */
@Volatile
var value1 = true
@Volatile
var value2 = true

fun main() = runBlocking {
    repeat(1_000) {
        value1 = true
        value2 = true

        val job1 = async(Dispatchers.Default) {
            value2 = false
            while (value1);
        }

        val job2 = async(Dispatchers.Default) {
            value1 = false
            while (value2);
        }

        job1.await()
        job2.await()
    }

    println("Success!")
}