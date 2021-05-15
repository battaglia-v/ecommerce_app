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
    val apple = Apple()
    val orange = Orange()

    userInput.lowercase().split(',').map {
        if (it == "apple") {
            itemsMap.put(
                apple, itemsMap.getOrDefault(
                    apple, 0
                ) + 1
            )
        } else if (it == "orange") {
            itemsMap.put(
                orange, itemsMap.getOrDefault(
                    orange, 0
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
    var total: Double = 0.0
    for ((key, value) in order.items) {
        total += (key.price * value.toDouble());
    }
    println("The total amount for your order is $$total")
    return total
}

fun printOrder(map: HashMap<ProductBase, Int>) {
    for ((key, value) in map) {
        println("${key}=$value")
    }
}

