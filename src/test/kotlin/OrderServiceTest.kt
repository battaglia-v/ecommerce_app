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

    "If user input includes 'APPLE,Apple,Orange,apple', then the order total should be $2.05." {
        val service = mockk<OrderService>()
        every { service.parseUserInput() } returns "APPLE,Apple,Orange,apple"
        val input = service.parseUserInput()
        val order = parseOrderInput(input)

        val result = calculateOrderTotal(order)

        assertEquals(2.05, result)
    }

    "If user input includes 'orange,orange,orange,apple', then the order total should be $1.35." {
        val service = mockk<OrderService>()
        every { service.parseUserInput() } returns "orange,orange,orange,apple"
        val input = service.parseUserInput()
        val order = parseOrderInput(input)

        val result = calculateOrderTotal(order)

        assertEquals(1.35, result)
    }

    "If order does not contain any items, throw an exception." {
        val emptyOrder = Order()

        val exception = shouldThrow<Exception> {
            calculateOrderTotal(emptyOrder)
        }
        exception.message shouldBe "Your order does not contain any items."
    }
})
