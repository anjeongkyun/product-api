package org.product.data.repositories

import org.product.data.model.BrandDataModel
import org.springframework.data.jpa.repository.JpaRepository

interface BrandJpaRepository : JpaRepository<BrandDataModel, Long>
