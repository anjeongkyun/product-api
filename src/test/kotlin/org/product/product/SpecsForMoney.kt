package org.product.product

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.product.domainmodel.valueobject.Money

class SpecsForMoney {
    @Test
    fun `Sut는 생성할 때 amount의 값이 0보다 작으면 예외를 반환한다`() {
        // Arrange
        val amount = -1

        // Act && Assert
        assertThrows<IllegalArgumentException> { Money(amount) }
    }

    @ParameterizedTest
    @CsvSource(
        "0",
        "1",
        "100",
    )
    fun `Sut는 생성할 때 amount의 값이 0보다 크거나 같으면 예외를 반환하지 않는다`(amount: Int) {
        // Act && Assert
        assertDoesNotThrow { Money(amount) }
    }
}
