package org.product

import autoparams.AutoSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.product.domainmodel.entities.Brand
import org.product.domainmodel.exceptions.InvariantViolationException

class SpecsForBrand {
    @Test
    fun `Sut는 생성할 때 name이 빈 값이면 예외를 던져야한다`() {
        // Arrange
        val name = ""

        // Act && Assert
        assertThrows<InvariantViolationException> {
            Brand.create(name)
        }
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 팩토리 메서드를 통해 객체를 생성할 수 있다`(
        name: String
    ) {
        val actual = Brand.create(name)

        assertThat(actual.id).isNull()
        assertThat(actual.name).isEqualTo(name)
    }
}
