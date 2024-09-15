package org.product.persistence.model

import jakarta.persistence.*
import org.product.domainmodel.entities.ProductCategory
import java.time.OffsetDateTime

@Entity
@Table(name = "products")
data class ProductDataModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val title: String,
    @Enumerated(EnumType.STRING)
    val category: ProductCategory,
    @ManyToOne
    @JoinColumn(name = "brand_id")
    val brand: BrandDataModel,
    val amount: Long,
    @Column(name = "created_date_time")
    val createdDateTime: OffsetDateTime,
    @Column(name = "updated_date_time")
    val updatedDateTime: OffsetDateTime,
    @Column(name = "is_deleted")
    val isDeleted: Boolean,
) {
    fun delete() = this.copy(isDeleted = true, updatedDateTime = OffsetDateTime.now())
}
