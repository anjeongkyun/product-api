package org.product.persistence.model

import jakarta.persistence.*
import org.product.domainmodel.entities.ProductCategory
import org.product.domainmodel.valueobject.Money
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
    val amount: Money,
    @Column(name = "created_date_time")
    val createdDateTime: OffsetDateTime,
    @Column(name = "updated_date_time")
    val updatedDateTime: OffsetDateTime,
)
