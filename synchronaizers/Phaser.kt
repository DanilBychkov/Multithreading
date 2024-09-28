package synchronaizers

import java.util.*
import java.util.concurrent.Phaser

private val PHASER = Phaser(1)

private class Passenger(
    val departure: Int,
    private val destination: Int,
    private val phaser: Phaser,
    val name: String
) : Runnable {

    init {
        println(this.toString() + " ждёт на остановке № " + this.departure)
    }

    override fun run() {
        try {
            println("$this на поезд.")

            while (phaser.phase < destination) { //Пока поезд не приедет на нужную остановку (фазу)
                phaser.arriveAndAwaitAdvance() //заявляем в каждой фазе о готовности и ждем
            }

            Thread.sleep(1000)
            println("$this покинул поезд.")
            phaser.arriveAndDeregister() //Отменяем регистрацию на нужной фазе
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    override fun toString(): String {
        return "Пассажир{$departure -> $destination}"
    }
}

fun main() {
    val passenger1 = Passenger(2, 6, PHASER, "Первый") //создаем пассажиров
    val passenger2 = Passenger(3, 5, PHASER, "Второй")
    val passenger3 = Passenger(2, 4, PHASER, "Третий")
    val passenger4 = Passenger(4, 6, PHASER, "Четвертый")
    val passenger5 = Passenger(1, 5, PHASER, "Пятый")
    val passenger6 = Passenger(1, 6, PHASER, "Шестой")
    val passengers = listOf(passenger1, passenger2, passenger3, passenger4, passenger5, passenger6)

    for (i in 0..6) {
        when (i) {
            0 -> {
                println("Поезд выехал со станции")
                PHASER.arrive()
            }

            6 -> {
                println("Поезд приехал на конечную станцию")
                PHASER.arriveAndDeregister() //Снимаем главный поток, поезд приехал в депо
            }

            else -> {
                val currentTrainStop = PHASER.phase
                println("Остановка № $currentTrainStop")
                Thread.sleep(7000)

                for (p in passengers)  //Проверяем, есть ли пассажиры на остановке
                    if (p.departure == currentTrainStop) {
                        PHASER.register() //Регистрируем пассажира, который будет участвовать в фазах
                        Thread(p, p.name).start() // и запускаем поток
                    }
                PHASER.arriveAndAwaitAdvance() //Сообщаем о своей готовности
            }
        }
    }
}
