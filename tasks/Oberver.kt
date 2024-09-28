package tasks

import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

private interface Observer {
    fun onChanged()
}

private interface Observable {

    fun registerObserver(observer: Observer)

    fun unregisterObserver(observer: Observer)

    fun notifyObserver()
}

private class ObservableImpl : Observable {

    private val lock = ReentrantReadWriteLock()
    private val observers = mutableListOf<Observer>()

    override fun registerObserver(observer: Observer) {
        lock.write { observers.add(observer) }
    }

    override fun unregisterObserver(observer: Observer) {
        lock.write { observers.remove(observer) }
    }

    override fun notifyObserver() {
        val copyObservers = lock.read {
            observers.toList()
        }
        copyObservers.forEach(Observer::onChanged)
    }
}

