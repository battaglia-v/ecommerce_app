import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import main.model.model.order.Order
import main.model.model.order.OrderService
import main.model.model.order.calculateOrderTotal

import main.model.model.order.parseOrderInput


class OrderServiceTest : StringSpec({

    "If user input includes invalid product, throw IllegalStateException error." {
        val service = mockk<OrderService>()
        every { service.parseUserInput() } returns "banana"
        val input = service.parseUserInput()

        val exception = shouldThrow<IllegalStateException> {
            parseOrderInput(input)
        }
        exception.message shouldBe "banana is not a valid product. Your order cannot be fulfilled."
    }

    "If user input includes 'APPLE,Apple,Orange,apple', then the order total should be $1.70." {
        val service = mockk<OrderService>()
        every { service.parseUserInput() } returns "APPLE,Apple,Orange,apple"
        val input = service.parseUserInput()
        val order = parseOrderInput(input)

        val result = calculateOrderTotal(order)

        assertEquals(1.70, result)
    }

    "If user input includes 'orange,orange,orange,apple', then the order total should be $1.10." {
        val service = mockk<OrderService>()
        every { service.parseUserInput() } returns "orange,orange,orange,apple"
        val input = service.parseUserInput()
        val order = parseOrderInput(input)

        val result = calculateOrderTotal(order)

        assertEquals(1.10, result)
    }

    "If user input includes 'orange,orange,orange', then the order total should be $.50, as 3 oranges are for the price of 2." {
        val service = mockk<OrderService>()
        every { service.parseUserInput() } returns "orange,orange,orange"
        val input = service.parseUserInput()
        val order = parseOrderInput(input)

        val result = calculateOrderTotal(order)

        assertEquals(.50, result)
    }

    "If user input includes 'apple,apple', then the order total should be $.60, as apples are buy one, get one free." {
        val service = mockk<OrderService>()
        every { service.parseUserInput() } returns "apple,apple"
        val input = service.parseUserInput()
        val order = parseOrderInput(input)

        val result = calculateOrderTotal(order)

        assertEquals(.60, result)
    }

    "If order does not contain any items, throw an exception." {
        val emptyOrder = Order()

        val exception = shouldThrow<Exception> {
            calculateOrderTotal(emptyOrder)
        }
        exception.message shouldBe "Your order does not contain any items."
    }
})
