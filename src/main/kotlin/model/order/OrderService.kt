package main.model.model.order

import main.model.model.products.Apple
import main.model.model.products.Orange

class OrderService {
    fun parseUserInput(): String = readLine()!!
}

fun parseOrderInput(userInput: String): Order {
    val newOrder = Order()
    val itemsMap = newOrder.items

    userInput.lowercase().split(',').map {
        when (it) {
            "apple" -> itemsMap.put(
                "apple", itemsMap.getOrDefault(
                    "apple", 0
                ) + 1
            )
            "orange" -> itemsMap.put(
                "orange", itemsMap.getOrDefault(
                    "orange", 0
                ) + 1
            )
            else -> {
                orderNotification(orderComplete = false)
                throw IllegalStateException("$it is not a valid product. Your order cannot be fulfilled.")
            }
        }
    }
    return newOrder
}

fun calculateOrderTotal(order: Order): Double {
    if (order.items.size < 1) {
        orderNotification(orderComplete = false)
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
            orderNotification(orderComplete = false)
            throw Exception("Your order contains items that cannot be calculated.")
        }
    }
    println("The total amount for your order is $$priceTotal")
    orderNotification(orderComplete = true)
    return priceTotal
}

fun printOrder(map: HashMap<String, Int>) {
    for ((key, value) in map) {
        println("${key}=$value")
    }
}

fun orderNotification(orderComplete: Boolean) {
    if (orderComplete)
        println("Order has been successfully placed - please allow 2-3 business days for shipment to arrive.")
    else if (!orderComplete)
        println("Unfortunately, your order cannot be processed at this time. Please try again later.")
    else {
        throw Exception("No order has been initiated yet.")
    }
}

fun stockNotification(outOfStock: Boolean, item: String) {
    if (outOfStock) {
        println("Unfortunately, we are out of stock of $item. Please check back again in 4-5 business days once the stock is replenished.")
        orderNotification(orderComplete = false)
    }
}
