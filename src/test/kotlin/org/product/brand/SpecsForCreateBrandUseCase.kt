package org.product.brand

import autoparams.AutoSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.product.TestRepositoryConfiguration
import org.product.api.config.DataSourceConfig
import org.product.contracts.commands.CreateBrandCommand
import org.product.domainmodel.repository.BrandRepository
import org.product.domainmodel.usecases.brand.CreateBrandUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [DataSourceConfig::class, TestRepositoryConfiguration::class])
class SpecsForCreateBrandUseCase {
    @Autowired
    private lateinit var brandRepository: BrandRepository

    @AutoSource
    @ParameterizedTest
    fun `Sut는 명령을 실행하면 올바르게 브랜드 엔터티를 생성해야한다`(name: String) {
        // Arrange
        val sut = CreateBrandUseCase(brandRepository)
        val command = CreateBrandCommand(name)

        // Act
        sut.execute(command)

        // Assert
        val actual = brandRepository.findAll().find { it.name === command.name }
        assertThat(actual).isNotNull
        assertThat(actual!!.id).isNotNull()
        assertThat(actual.name).isEqualTo(command.name)
    }
}
