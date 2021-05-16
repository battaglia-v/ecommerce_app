package main.model

import main.model.model.order.OrderService
import main.model.model.order.calculateOrderTotal
import main.model.model.order.parseOrderInput

import main.model.model.order.printOrder


fun main() {
    val userInput = OrderService().parseUserInput()
    val newOrder = parseOrderInput(userInput)
    printOrder(newOrder.items)
    calculateOrderTotal(newOrder)
}
