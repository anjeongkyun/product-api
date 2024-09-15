package org.product.brand

import autoparams.AutoSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.product.TestRepositoryConfiguration
import org.product.api.config.DataSourceConfig
import org.product.contracts.commands.UpdateBrandCommand
import org.product.domainmodel.entities.Brand
import org.product.domainmodel.repository.BrandRepository
import org.product.domainmodel.usecases.brand.UpdateBrandUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [DataSourceConfig::class, TestRepositoryConfiguration::class])
class SpecsForUpdateBrandUseCase {
    @Autowired
    private lateinit var brandRepository: BrandRepository

    @AutoSource
    @ParameterizedTest
    fun `Sut는 명령의 ID에 해당하는 엔터티의 이름을 수정할 수 있다`(
        entity: Brand,
        nameToChange: String,
    ) {
        // Arrange
        val saved = brandRepository.create(entity)
        val sut = UpdateBrandUseCase(brandRepository)
        val command =
            UpdateBrandCommand(
                id = saved.id!!,
                name = nameToChange,
            )

        // Act
        sut.execute(command)

        // Assert
        val actual = brandRepository.findAll().find { it.name === command.name }
        assertThat(actual).isNotNull
        assertThat(actual!!.id).isEqualTo(command.id)
        assertThat(actual.name).isEqualTo(command.name)
    }
}
