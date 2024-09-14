package org.product.data.model

import jakarta.persistence.*

@Entity
@Table(name = "brands")
data class BrandDataModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Column(unique = true)
    val name: String,

    val isDeleted: Boolean,
) {
    fun delete(): BrandDataModel = this.copy(isDeleted = true)
}
