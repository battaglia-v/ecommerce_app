package main.model.model.order

import main.model.model.products.Apple
import main.model.model.products.Orange
import main.model.model.products.ProductBase

class OrderService {
    fun parseUserInput(): String = readLine()!!
}

fun parseOrderInput(userInput: String): Order {
    val newOrder = Order()
    val itemsMap = newOrder.items

    userInput.lowercase().split(',').map {
        if (it == "apple") {
            itemsMap.put(
                "apple", itemsMap.getOrDefault(
                    "apple", 0
                ) + 1
            )
        } else if (it == "orange") {
            itemsMap.put(
                "orange", itemsMap.getOrDefault(
                    "orange", 0
                ) + 1
            )
        } else {
            throw IllegalStateException("$it is not a valid product. Your order cannot be fulfilled.")
        }
    }
    return newOrder
}

fun calculateOrderTotal(order: Order): Double {
    if (order.items.size < 1) {
        throw Exception("Your order does not contain any items.")
    }
    var priceTotal = 0.0
    val appleCount = order.items["apple"]
    val orangeCount = order.items["orange"]

    for ((key) in order.items) {
        if (key == "apple" && appleCount != null) {
            priceTotal += if (appleCount % 2 == 0) {
                (appleCount / 2) * Apple().price
            } else {
                val remainder = appleCount % 2
                ((appleCount / 2) * Apple().price) + (remainder * Apple().price)
            }
        } else if (key == "orange" && orangeCount != null) {
            priceTotal += if (orangeCount % 3 == 0) {
                (orangeCount - (orangeCount / 3)) * Orange().price
            } else {
                val remainder = orangeCount % 3
                ((orangeCount - (orangeCount / 3)) * Orange().price) + (remainder * Orange().price)
            }
        } else {
            throw Exception("Your order contains items that cannot be calculated.")
        }
    }
    println("The total amount for your order is $$priceTotal")
    return priceTotal
}

fun printOrder(map: HashMap<String, Int>) {
    for ((key, value) in map) {
        println("${key}=$value")
    }
}
